package promisor.promisor.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import promisor.promisor.domain.member.dto.*;
import promisor.promisor.domain.member.service.MemberService;
import promisor.promisor.global.auth.JwtAuth;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "Member register", description = "회원 가입")
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid final SignUpDto request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/members").toUriString());
        return ResponseEntity.created(uri).body(memberService.save(request));
    }

    @Operation(summary = "Register confirm", description = "회원가입 이메일 인증")
    @GetMapping("/confirm")
    public ResponseEntity<MemberResponse> confirm(@RequestParam("token") final String token) {
        MemberResponse memberResponse = memberService.confirmToken(token);
        return ResponseEntity.ok().body(memberResponse);
    }

    @Operation(summary = "Login", description = "사용자 인증")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginDto loginDto) {
        LoginResponse response = memberService.login(loginDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Refresh token", description = "리프레시 토큰 발급")
    @GetMapping("/token/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody @Valid final LoginDto.GetRefreshTokenDto getRefreshTokenDto) {
        LoginResponse response = memberService.refreshToken(getRefreshTokenDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Modify member info", description = "사용자 정보 수정")
    @PatchMapping("/modify")
    public ResponseEntity<ModifyMemberResponse> modifyInfo(@JwtAuth final String email,
                                                           @RequestBody @Valid final ModifyMemberDto modifyMemberDto) {
        return ResponseEntity.ok().body(memberService.modifyInfo(email, modifyMemberDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileMemberResponse> getInfo(@JwtAuth final String email){
        return ResponseEntity.ok().body(memberService.getInfo(email));
    }
}