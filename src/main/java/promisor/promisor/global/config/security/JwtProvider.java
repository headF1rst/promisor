package promisor.promisor.global.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import promisor.promisor.domain.member.dto.LoginDto;
import promisor.promisor.domain.member.service.MemberService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final String secretKey = "sd92dfs0-1544-32da-bd21-bd234slkj";
    private final Long accessExpireTime = 60 * 60 * 1000L; // 3시간
    private final Long refreshExpireTime = ((60 * 60 * 1000L) * 24) * 60; // 60일
    private final MemberService memberService;

    public String createAccessToken(LoginDto loginDto) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", loginDto.getEmail());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + accessExpireTime);

        String jwt = Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("member")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public Map<String, String> createRefreshToken(LoginDto loginDto) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", loginDto.getEmail());

        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + refreshExpireTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(expiration);

        String jwt = Jwts
                .builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("member")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        Map<String, String> result = new HashMap<>();
        result.put("refreshToken", jwt);
        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return result;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserInfo(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    public boolean validateJwtToken(ServletRequest request, String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException err) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException err) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException err) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException err) {
            request.setAttribute("exception", "IllegalArgumentException");
        }
        return false;
    }
}
