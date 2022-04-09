package promisor.promisor.domain.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.service.RelationService;
import promisor.promisor.global.auth.JwtAuth;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @PostMapping("/{friendId}")
    public ResponseEntity<Void> followFriend(@JwtAuth String email,
                                             @PathVariable("friendId") Long friendId) {
        relationService.followFriend(email, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberResponse> searchFriend(@JwtAuth String email,
                                                       @RequestParam String findEmail) {
        MemberResponse response = relationService.searchFriend(email, findEmail);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MemberResponse>> getFriendList(@JwtAuth String email) {

        List<MemberResponse> response = relationService.getFriendList(email);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@JwtAuth String email,
                                             @PathVariable("friendId") Long friendId) {
        relationService.deleteFriend(email, friendId);
        return ResponseEntity.ok().build();
    }
}
