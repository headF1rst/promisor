package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promisor.promisor.domain.auth.EnableAuth;
import promisor.promisor.domain.member.dto.FollowFriendRequest;
import promisor.promisor.domain.member.service.MemberService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> followFriend(@EnableAuth String email,
                                             @RequestBody FollowFriendRequest request) {
        memberService.followFriend(email, request);
        return ResponseEntity.ok().build();
    }

}
