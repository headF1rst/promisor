package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.member.dto.FollowFriendRequest;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.service.MemberService;
import promisor.promisor.global.auth.JwtAuth;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> followFriend(@JwtAuth String email,
                                            @RequestBody FollowFriendRequest request) {
        memberService.followFriend(email, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberResponse> searchFriend(@JwtAuth String email,
                                                       @RequestParam String findEmail) {
        MemberResponse response = memberService.searchFriend(email, findEmail);
        return ResponseEntity.ok().body(response);
    }

}
