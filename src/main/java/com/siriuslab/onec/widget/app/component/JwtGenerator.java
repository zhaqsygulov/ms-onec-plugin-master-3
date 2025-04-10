package com.siriuslab.onec.widget.app.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.siriuslab.onec.widget.app.service.AppConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtGenerator {
    private final String secretKey;
    private final String appUid;
    private static final long MAX_TOKEN_LIFETIME = 5 * 60 * 1000; // Максимальное время жизни токена в миллисекундах (например, 5 минут)
    public JwtGenerator(AppConfigService appConfigService) {
        this.secretKey = appConfigService.getValueByKey("jwt.secret");
        this.appUid = appConfigService.getValueByKey("appUid");
    }

    public String generateToken() {
        long nowMillis = System.currentTimeMillis();

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(appUid)
                .withIssuedAt(new Date(nowMillis))
                .withExpiresAt(new Date(nowMillis + MAX_TOKEN_LIFETIME))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public void verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        log.info("Decoded Token Claims: {}", jwt.getClaims());
    }
}
