package promisor.promisor.global.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import promisor.promisor.domain.member.exception.EmailEmptyException;
import promisor.promisor.global.error.ErrorCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String token = jwtProvider.resolveToken(request);

        try {
            if (token != null && jwtProvider.validateJwtToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException err) {
            logger.error("token is expired and not valid anymore", err);
            throw new JwtException("토큰 기한이 만료되었습니다.");
        } catch (IllegalArgumentException err) {
            logger.error("an error occurred during getting email from token", err);
            throw new JwtException("유효하지 않은 토큰정보 입니다.");
        } catch (SignatureException err) {
            logger.error("Authentication Failed. Username or Password not valid", err);
            throw new JwtException("사용자 인증에 실패하였습니다.");
        } catch (BadCredentialsException err) {
            logger.error("Incorrect email or password.");
            throw new BadCredentialsException("이메일 또는 비밀번호를 다시 확인해주세요.");
        }
        chain.doFilter(request, response);
    }
}
