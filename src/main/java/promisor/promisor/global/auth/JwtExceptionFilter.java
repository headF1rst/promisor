package promisor.promisor.global.auth;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import promisor.promisor.global.token.JwtExceptionResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException err) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, err);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable err) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        JwtExceptionResponse jwtExceptionResponse = new JwtExceptionResponse(err.getMessage(), HttpStatus.UNAUTHORIZED);
        response.getWriter().write(jwtExceptionResponse.convertToJson());
    }
}
