package promisor.promisor.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.member.dto.SignUpDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public String register(@RequestBody @Valid final SignUpDto request) {

        return memberService.register(request);
    }

    @GetMapping("/members/confirm")
    public String confirm(@RequestParam("token") String token) {

        return memberService.confirmToken(token);
    }
}
