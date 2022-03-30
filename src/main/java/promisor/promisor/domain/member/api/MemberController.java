package promisor.promisor.domain.member.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.dto.SignUpDto;
import promisor.promisor.domain.member.service.MemberService;
import promisor.promisor.global.token.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid final SignUpDto request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/members").toUriString());
        return ResponseEntity.created(uri).body(memberService.save(request));
    }

    @GetMapping("/confirm")
    public ResponseEntity<MemberResponse> confirm(@RequestParam("token") String token) {
        MemberResponse memberResponse = memberService.confirmToken(token);
        return ResponseEntity.ok().body(memberResponse);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            String refreshToken = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // Utility class 리팩토링
            JWTVerifier verifier = JWT.require(algorithm).build(); // Utility class로 리팩토링
            DecodedJWT decodedJWT = verifier.verify(refreshToken); // Utility class로 리팩토링
            String email = decodedJWT.getSubject(); // Utility class로 리팩토링

            Member member = memberService.getMember(email);
            String accessToken = JWT.create()
                    .withSubject(member.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("memberRole", member.getRole())
                    .sign(algorithm);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } else {
            throw new InvalidTokenException();
        }
    }
}
