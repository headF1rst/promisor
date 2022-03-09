package promisor.promisor.infra.email;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import promisor.promisor.global.secret.SecretKey;
import promisor.promisor.infra.email.exception.EmailConfirmedException;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public String createToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + secretKey.getJwtValidityTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey.getJwtSecretKey())
                .compact();
    }

    public String extractEmail(String token) {
        if (!validate(token)) {
//            TODO: 별도의 INVALID_TOKEN 예외 만들기
            throw new EmailConfirmedException();
        }
        return Jwts.parser().setSigningKey(secretKey.getJwtSecretKey()).parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getJwtSecretKey())
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
//            TODO: 별도의 INVALID_TOKEN 예외 만들기
            throw new EmailConfirmedException();
        }
    }
}
