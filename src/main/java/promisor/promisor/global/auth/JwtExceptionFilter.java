package promisor.promisor.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import promisor.promisor.domain.member.exception.EmailEmptyException;
import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.ErrorResponse;
import promisor.promisor.global.token.JwtExceptionResponse;
import promisor.promisor.infra.email.exception.EmailNotValid;

import javax.security.sasl.AuthenticationException;
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
        } catch (BadCredentialsException err) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, err);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable err) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JwtExceptionResponse jwtExceptionResponse = new JwtExceptionResponse(401, "C008", err.getMessage());

        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(mapper.writeValueAsString(jwtExceptionResponse.convertToJson()));
    }
}
