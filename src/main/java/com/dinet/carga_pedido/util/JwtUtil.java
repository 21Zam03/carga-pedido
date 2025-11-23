package com.dinet.carga_pedido.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${spring.security.oauth2.resource-server.jwt.key-value}")
    private String secret;

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    public String generateToken(Map<String, Object> claims) {
        return JWT.create()
                .withSubject(claims.get("username").toString())
                .withPayload(claims)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 180)) // 180 min
                .sign(Algorithm.HMAC256(secret));
    }

}
