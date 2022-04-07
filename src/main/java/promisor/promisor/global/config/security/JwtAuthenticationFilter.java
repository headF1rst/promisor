package promisor.promisor.global.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
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
            if (jwtProvider.validateJwtToken(token) && token != null) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException err) {
            err.printStackTrace();
            request.setAttribute("exception", ErrorCode.TOKEN_EXPIRED);
        } catch (JwtException err) {
            err.printStackTrace();
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN);
        }
        chain.doFilter(request, response);
    }
}
