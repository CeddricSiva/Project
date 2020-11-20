package com.nvp.data.service.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class RSAUtil {

	private RSAUtil() {
	}

	/**
	 * Convert base64 string to rsa private key
	 * @param base64PrivateKey
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String base64PrivateKey) {
		RSAPrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			if (keyFactory != null) {
				privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
			}
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return privateKey;
	}
	
	/**
	 * Convert base64 string to rsa public key
	 * @param base64PublicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static RSAPublicKey getPublicKey(String base64PublicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		RSAPublicKey publicKey = null;
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		return publicKey;
	}
}