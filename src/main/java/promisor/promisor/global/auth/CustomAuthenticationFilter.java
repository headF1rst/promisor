package promisor.promisor.global.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;
import promisor.promisor.domain.member.dto.request.LoginRequest;
import promisor.promisor.domain.member.exception.LoginInfoNotFoundException;
import promisor.promisor.domain.member.exception.EmailEmptyException;
import promisor.promisor.domain.member.exception.PasswordEmptyException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setAuthenticationManager(authenticationManager);
    }

    /**
     * 인증 절차 수행. username, password를 받아서 UsernamePasswordAuthenticationToken 생성
     * @param request username, password
     * @param response
     * @return UsernamePasswordAuthenticationToken
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken;

        if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            // 요청이 Json 형태인 경우
            try {
                LoginRequest loginRequest = objectMapper.readValue(request.getReader().lines().collect(Collectors.joining()), LoginRequest.class);
                if (loginRequest.getEmail().isEmpty()) {
                    throw new EmailEmptyException();
                }

                if (loginRequest.getPassword().isEmpty()) {
                    throw new PasswordEmptyException();
                }
                log.info("Email is: {}", loginRequest.getEmail()); log.info("Password is: {}", loginRequest.getPassword());
                // 아직 인증되지 않음
                authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
                log.info("Token = {}", authenticationToken);
            } catch (IOException err) {
                err.printStackTrace();
                throw new AuthenticationServiceException("Request Content-Type(application/json) Parsing Error");
            }
        } else {
            // 요청이 form 형태인 경우
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            log.info("Email is: {}", email); log.info("Password is: {}", password);
            authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        }

        this.setDetails(request, authenticationToken);
        Authentication auth = this.getAuthenticationManager().authenticate(authenticationToken);
        log.info("auth is : {}", auth);

        if (!auth.isAuthenticated()) {
            throw new LoginInfoNotFoundException();
        }
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
//        TODO: "secret" 다른 곳에 저장하거나 encrypt 시키기
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("memberRole", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
