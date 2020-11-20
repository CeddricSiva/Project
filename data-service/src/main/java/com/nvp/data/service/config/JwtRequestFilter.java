package com.nvp.data.service.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvp.data.service.bean.ApiResponseBean;
import com.nvp.data.service.util.ByteArrayPrinter;
import com.nvp.data.service.util.CustomHandler;
import com.nvp.data.service.util.CustomHttpServletRequest;
import com.nvp.data.service.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	CustomHandler customHandler;
	private static MessageDigest messageDigest = null;
	private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

	/**
	 * Jwt Request filter to validate signed Jwt token with the request payload
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		ByteArrayPrinter pw = new ByteArrayPrinter();
		final String requestTokenHeader = request.getHeader("Authorization");
		CustomHttpServletRequest wrappedRequest = new CustomHttpServletRequest(request);
		ObjectMapper mapper = new ObjectMapper();
		Object req = null;
		String payload = null;
		String checksums = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				checksums = jwtTokenUtil.getPayloadFromToken(jwtToken);
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException e) {
				log.warn("JWT Token has expired");
			}
			req = mapper.readValue(readRequestBody(wrappedRequest), Object.class);
			payload = generateChecksum(mapper.writeValueAsString(req));

		} else {
			log.warn("JWT Token does not begin with Bearer String");
		}

		if (checksums != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, payload))) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							null, null, null);

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException e) {
				log.warn("JWT Token not matches with the request payload  or Token expired");
			}
		}

		/**
		 * Custom response wrapper to generate Jwt signed response
		 */
		HttpServletResponse wrappedResp = new HttpServletResponseWrapper(response) {

			@Override
			public PrintWriter getWriter() {
				return pw.getWriter();
			}

			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return pw.getStream();
			}

		};

		/**
		 * Converts response to jwt signed token
		 */

		if (req != null) {
			chain.doFilter(wrappedRequest, wrappedResp);
			String jwtResToken = jwtTokenUtil.generateToken(customHandler.getBody());
			ApiResponseBean resp = new ApiResponseBean();
			resp.setJwtToken(jwtResToken);
			pw.getStream().write(mapper.writeValueAsBytes(resp));
			byte[] bytes = pw.toByteArray();
			response.getOutputStream().write(bytes);
			wrappedResp.setHeader("Content-Type", "application/json");
		} else {
			chain.doFilter(wrappedRequest, wrappedResp);

			pw.getStream().write(mapper.writeValueAsBytes(customHandler.getBody()));
			byte[] bytes = pw.toByteArray();
			response.getOutputStream().write(bytes);
		}
	}

	/**
	 * To extract response body from the HttpServletRequest
	 */
	public String readRequestBody(CustomHttpServletRequest wrappedRequest) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		InputStream inputStream = wrappedRequest.getInputStream();
		if (inputStream != null) {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			char[] charBuffer = new char[128];
			int bytesRead = -1;
			while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
		} else {
			stringBuilder.append("");
		}

		return stringBuilder.toString();
	}

	private static void initializeMessageDigest() {
		if (messageDigest == null) {
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				log.warn("NoSuchAlgorithmException");
			}
		}

	}

	/**
	 * Converts request payload String to checksum
	 */
	public static String generateChecksum(String input) {
		initializeMessageDigest();
		byte[] digest = messageDigest.digest(input.getBytes());
		return DatatypeConverter.printHexBinary(digest);
	}

}
