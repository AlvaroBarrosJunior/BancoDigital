package com.bancodigital.utils;

import java.util.Date;

import com.bancodigital.model.ClienteModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtils {
	
	private static final long expirationTime = 1800000;
	private static final String key = "${token.key}";
	
	public static String generateToken(ClienteModel cliente) {
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setSubject(cliente.getIdCliente().toString())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, key).compact();
	}
	
	public static Claims decodeToken(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
	}

}
