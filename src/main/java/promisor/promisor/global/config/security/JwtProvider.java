package promisor.promisor.global.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import promisor.promisor.domain.member.service.CustomUserDetailService;
import promisor.promisor.global.secret.SecretConfig;
import promisor.promisor.global.secret.SecretKey;
import promisor.promisor.global.token.exception.InvalidTokenException;
import promisor.promisor.global.token.exception.TokenExpiredException;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final SecretKey secret;
    private final CustomUserDetailService customUserDetailService;

    public String createAccessToken(String email) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", email);

        log.info("accessExpireTime : '{}'", secret.getJwtValidityTime());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + secret.getJwtValidityTime());

        return Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("member")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret.getJwtSecretKey())
                .compact();
    }

    public Map<String, String> createRefreshToken(String email) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", email);

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + secret.getRefreshValidityTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(expiration);

        String jwt = Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("member")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret.getJwtSecretKey())
                .compact();

        Map<String, String> result = new HashMap<>();
        result.put("refreshToken", jwt);
        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return result;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.extractEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT decoding 이메일
    public String extractEmail(String token) {
        return (String) Jwts.parser().setSigningKey(secret.getJwtSecretKey()).parseClaimsJws(token).getBody().get("email");
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret.getJwtSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException err) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException err) {
            throw new TokenExpiredException();
        }
    }
}
