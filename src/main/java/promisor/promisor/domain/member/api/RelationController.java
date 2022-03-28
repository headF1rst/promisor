package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.auth.EnableAuth;
import promisor.promisor.domain.member.dto.FollowFriendRequest;
import promisor.promisor.domain.member.dto.FollowFriendResponse;
import promisor.promisor.domain.member.service.MemberService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<FollowFriendResponse> followFriend(@RequestBody FollowFriendRequest request) {
        memberService.followFriend(request);
        return ResponseEntity.ok().build();
    }

}
