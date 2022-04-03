package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import promisor.promisor.domain.member.dto.LoginDto;
import promisor.promisor.domain.member.dto.LoginResponse;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.dto.SignUpDto;
import promisor.promisor.domain.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDto loginDto) {
        LoginResponse response = memberService.login(loginDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody @Valid LoginDto.GetRefreshTokenDto getRefreshTokenDto,
                                                      HttpServletRequest request) {
        LoginResponse response = memberService.refreshToken(getRefreshTokenDto, request);
        return ResponseEntity.ok().body(response);
    }
}
