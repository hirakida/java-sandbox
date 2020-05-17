package com.example.web;

import static com.example.config.LineLoginConfig.LOGIN_BASE_URL;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.config.LineLoginProperties;
import com.example.model.IdTokenPayload;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtHelper {
    private static final String NONCE = "nonce";
    private final LineLoginProperties properties;

    public IdTokenPayload getPayload(String idToken, String issuedNonce) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(properties.getChannelSecret()))
                                  .withIssuer(LOGIN_BASE_URL)
                                  .withAudience(properties.getChannelId())
                                  .build();
        DecodedJWT jwt = verifier.verify(idToken);
        Map<String, Claim> claims = jwt.getClaims();

        if (!claims.containsKey(NONCE)) {
            throw new IllegalArgumentException("nonce not found.");
        }
        String nonce = claims.get(NONCE).asString();
        if (!nonce.equals(issuedNonce)) {
            throw new IllegalArgumentException(String.format("Invalid nonce. %s %s", issuedNonce, nonce));
        }

        return toPayload(claims);
    }

    private static IdTokenPayload toPayload(Map<String, Claim> claims) {
        IdTokenPayload payload = new IdTokenPayload();

        if (claims.containsKey("iss")) {
            payload.setIss(claims.get("iss").asString());
        }
        if (claims.containsKey("sub")) {
            payload.setSub(claims.get("sub").asString());
        }
        if (claims.containsKey("aud")) {
            payload.setAud(claims.get("aud").asString());
        }

        if (claims.containsKey("exp")) {
            payload.setExp(claims.get("exp").asLong());
        }
        if (claims.containsKey("iat")) {
            payload.setIat(claims.get("iat").asLong());
        }
        if (claims.containsKey("auth_time")) {
            payload.setAuthTime(claims.get("auth_time").asLong());
        }

        if (claims.containsKey(NONCE)) {
            payload.setNonce(claims.get(NONCE).asString());
        }
        if (claims.containsKey("amr")) {
            payload.setAmr(claims.get("amr").asArray(String.class));
        }

        if (claims.containsKey("name")) {
            payload.setName(claims.get("name").asString());
        }
        if (claims.containsKey("picture")) {
            payload.setPicture(claims.get("picture").asString());
        }
        if (claims.containsKey("email")) {
            payload.setEmail(claims.get("email").asString());
        }

        return payload;
    }
}
