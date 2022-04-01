package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.auth.EnableAuth;
import promisor.promisor.domain.member.dto.FollowFriendRequest;
import promisor.promisor.domain.member.dto.FollowFriendResponse;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.service.MemberService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> followFriend(@RequestBody FollowFriendRequest request) {
        memberService.followFriend(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberResponse> searchFriend(@EnableAuth Long id,
            @RequestParam String email) {
        MemberResponse response = memberService.searchFriend(id, email);
        return ResponseEntity.ok().body(response);
    }

}
