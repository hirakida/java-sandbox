package com.example.web;

import static com.example.web.config.LineLoginConfig.AUTHORIZATION_BASE_URL;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.web.config.LineLoginProperties;
import com.example.web.model.IdTokenPayload;

@Component
public class JwtHelper {
    private final JWTVerifier verifier;

    public JwtHelper(LineLoginProperties properties) {
        verifier = JWT.require(Algorithm.HMAC256(properties.getChannelSecret()))
                      .withIssuer(AUTHORIZATION_BASE_URL)
                      .withAudience(properties.getChannelId())
                      .build();
    }

    public IdTokenPayload getPayload(String idToken, String nonce) {
        DecodedJWT jwt = verifier.verify(idToken);

        String decodedNonce = jwt.getClaim("nonce").asString();
        if (decodedNonce == null || !decodedNonce.equals(nonce)) {
            throw new ResponseStatusException(FORBIDDEN, String.format("Invalid nonce. %s, %s",
                                                                       nonce, decodedNonce));
        }

        IdTokenPayload payload = new IdTokenPayload();
        payload.setIss(jwt.getIssuer());
        payload.setSub(jwt.getSubject());
        payload.setAud(jwt.getAudience().get(0));
        payload.setExp(jwt.getExpiresAt().getTime() / 1000);
        payload.setIat(jwt.getIssuedAt().getTime() / 1000);
        if (!jwt.getClaim("auth_time").isNull()) {
            payload.setAuthTime(jwt.getClaim("auth_time").asLong());
        }
        payload.setNonce(decodedNonce);
        payload.setAmr(jwt.getClaim("amr").asArray(String.class));
        payload.setName(jwt.getClaim("name").asString());
        payload.setPicture(jwt.getClaim("picture").asString());
        payload.setEmail(jwt.getClaim("email").asString());
        return payload;
    }
}
