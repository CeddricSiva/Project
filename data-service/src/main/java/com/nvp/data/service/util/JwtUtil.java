package com.nvp.data.service.util;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${rsa.request.public.key}")
	private String rsaPublicKey;

	@Value("${rsa.response.private.key}")
	private String rsaPrivateKey;

	/**
	 * Extract request payload from Jwt Claims
	 * 
	 * @param token
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public String getPayloadFromToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {

		return (String) getAllClaimsFromToken(token).get("checksum");
	}

	/**
	 * Extract Expiration time from Jwt Claims
	 * 
	 * @param token
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public Date getExpirationDateFromToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * Extract claim from Jwt Token
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return Jwts.parser().setSigningKey(RSAUtil.getPublicKey(rsaPublicKey)).parseClaimsJws(token).getBody();

	}

	/**
	 * Checks token expiration
	 * 
	 * @param token
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private Boolean isTokenExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration() {
		return false;
	}

	/**
	 * Add response payload to claims
	 * 
	 * @param object
	 * @return
	 */
	public String generateToken(Object object) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("payload", object);
		claims.put("Source App Name", "NVP Data Service");
		return createToken(claims);
	}

	/**
	 * Generate JWT response token
	 * 
	 * @param claims
	 * @return
	 */
	private String createToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setHeaderParam("typ", "JWT")
				.setHeaderParam("kid", "bac1200f-3624-40da-81e7-a445715de1d5")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TOKEN_VALIDITY * 5))
				.signWith(SignatureAlgorithm.RS256, RSAUtil.getPrivateKey(rsaPrivateKey)).compact();
	}

	public Boolean canTokenBeRefreshed(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return (!isTokenExpired(token) || ignoreTokenExpiration());
	}

	/**
	 * JWT token validation Returns true if token not expired
	 * 
	 * @param token
	 * @param checksum
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public Boolean validateToken(String token, String payload)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final String checksum = getPayloadFromToken(token);
		return (checksum != null && !checksum.isEmpty() && checksum.equals(payload) && !isTokenExpired(token));
	}

}
