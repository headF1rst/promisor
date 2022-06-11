package promisor.promisor.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.member.dto.response.MemberResponse;
import promisor.promisor.domain.member.service.RelationService;
import promisor.promisor.global.auth.JwtAuth;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @Operation(summary = "Follow friend", description = "친구 추가")
    @PostMapping("/{id}")
    public ResponseEntity<Void> followFriend(@JwtAuth final String email,
                                             @PathVariable("id") final Long friendId) {
        relationService.followFriend(email, friendId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Search friend by email", description = "이메일 통한 친구 검색")
    @GetMapping
    public ResponseEntity<MemberResponse> searchFriend(@JwtAuth final String email,
                                                       @RequestParam final String findEmail) {
        MemberResponse response = relationService.searchFriend(email, findEmail);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get friend list", description = "친구 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<List<MemberResponse>> getFriendList(@JwtAuth final String email) {

        List<MemberResponse> response = relationService.getFriendList(email);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete Friend", description = "친구 삭제")
    @PatchMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@JwtAuth final String email,
                                             @PathVariable("friendId") final Long friendId) {
        relationService.deleteFriend(email, friendId);
        return ResponseEntity.ok().build();
    }
}
