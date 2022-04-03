package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.member.dto.FollowFriendRequest;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> followFriend(@RequestBody FollowFriendRequest request,
                                             @RequestHeader String token) {
        memberService.followFriend(request, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberResponse> searchFriend(Long id,
                                                       @RequestParam String email) {
        MemberResponse response = memberService.searchFriend(id, email);
        return ResponseEntity.ok().body(response);
    }

}
