package com.example;

import static com.example.AuthorizationHelper.AUTHORIZATION_BASE_URL;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.model.IdTokenPayload;

@Component
public class JwtHelper {
    private final JWTVerifier verifier;

    public JwtHelper(LineLoginProperties properties) {
        verifier = JWT.require(Algorithm.HMAC256(properties.getChannelSecret()))
                      .withIssuer(AUTHORIZATION_BASE_URL)
                      .withAudience(properties.getChannelId())
                      .build();
    }

    public String getSubject(String idToken) {
        DecodedJWT jwt = verifier.verify(idToken);
        return jwt.getSubject();
    }

    public IdTokenPayload getPayload(String idToken) {
        DecodedJWT jwt = verifier.verify(idToken);
        IdTokenPayload payload = new IdTokenPayload();
        payload.setIss(jwt.getIssuer());
        payload.setSub(jwt.getSubject());
        payload.setAud(jwt.getAudience().get(0));
        payload.setExp(jwt.getExpiresAt().getTime() / 1000);
        payload.setIat(jwt.getIssuedAt().getTime() / 1000);
        if (!jwt.getClaim("auth_time").isNull()) {
            payload.setAuthTime(jwt.getClaim("auth_time").asLong());
        }
        payload.setNonce(jwt.getClaim("nonce").asString());
        payload.setAmr(jwt.getClaim("amr").asArray(String.class));
        payload.setName(jwt.getClaim("name").asString());
        payload.setPicture(jwt.getClaim("picture").asString());
        payload.setEmail(jwt.getClaim("email").asString());
        return payload;
    }
}
