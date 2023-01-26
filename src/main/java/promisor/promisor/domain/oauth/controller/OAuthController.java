package promisor.promisor.domain.oauth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.member.dto.response.LoginResponse;
import promisor.promisor.domain.oauth.service.OAuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/kakao/login")
    public ResponseEntity<LoginResponse> kakaoSignIn(@RequestBody @Valid final String accessToken) {
        LoginResponse loginResponse = oAuthService.login(accessToken);
        return ResponseEntity.ok().body(loginResponse);
    }
}
