package promisor.promisor.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import promisor.promisor.domain.member.service.CustomUserDetailService;
import promisor.promisor.global.auth.secret.SecretKey;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final SecretKey secret;
    private final CustomUserDetailService customUserDetailService;

    public String createAccessToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validityTime = new Date(now.getTime() + secret.getJwtValidityTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validityTime)
                .signWith(SignatureAlgorithm.HS256, secret.getJwtSecretKey())
                .compact();
    }

    public Map<String, String> createRefreshToken(String payload) {

        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validityTime = new Date(now.getTime() + secret.getJwtValidityTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(validityTime);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validityTime)
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

    public String extractEmail(String token) {
        return (String) Jwts.parser().setSigningKey(secret.getJwtSecretKey()).parseClaimsJws(token).getBody().get("email");
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        return header.replace("Bearer ", "");
    }

    public boolean validateJwtToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret.getJwtSecretKey())
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 토큰 정보입니다.");
        }
    }

    public Long getTokenExpireTime(String accessToken) {

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = accessToken.split("\\.");
        ObjectMapper mapper = new ObjectMapper();
        String payload = new String(decoder.decode(parts[1]));
        Map exp = null;

        try {
            exp = mapper.readValue(payload, Map.class);
            return ((Number) exp.get("exp")).longValue();
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }
}
