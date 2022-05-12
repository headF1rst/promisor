package promisor.promisor.domain.team.api;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.team.dto.*;
import promisor.promisor.domain.team.exception.TooManyRequestException;
import promisor.promisor.domain.team.service.TeamService;
import promisor.promisor.global.auth.JwtAuth;
import promisor.promisor.global.token.exception.InvalidTokenException;


import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    private Bucket bucket;

    public void BucketController() {

        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMillis(30000)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Operation(summary = "Create group", description = "그룹 생성")
    @PostMapping
    public ResponseEntity<Void> createGroup(@JwtAuth String email,
                                            @RequestBody @Valid final CreateTeamDto request) {

        if (bucket.tryConsume(1)) {
            teamService.createGroup(email, request);
        } else {
            throw new TooManyRequestException();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit group name", description = "그룹 이름 수정")
    @PatchMapping
    public ResponseEntity<ChangeTeamNameResponse> editGroup(@JwtAuth String email,
                                                            @RequestBody @Valid final EditTeamDto request) {
        return ResponseEntity.ok().body(teamService.editGroup(email, request));
    }

    @Operation(summary = "Leave group", description = "그룹 탈퇴")
    @PatchMapping("/leave/{groupId}")
    public ResponseEntity<LeaveTeamResponse> leaveGroup(@JwtAuth String email, @PathVariable("groupId")Long groupId){
        return ResponseEntity.ok().body(teamService.leaveGroup(email, groupId));
    }

    @Operation(summary = "Invite group", description = "그룹 초대")
    @PostMapping("/invite")
    public ResponseEntity<InviteTeamResponse> inviteGroup(@JwtAuth String email,
                                                          @RequestBody @Valid final InviteTeamDto request){
        return ResponseEntity.ok().body(teamService.inviteGroup(email,request));
    }

    @Operation(summary = "Delegate GroupLeader", description = "그룹장 위임")
    @PatchMapping("/delegate")
    public ResponseEntity<DelegateLeaderResponse> delegateLeader(@JwtAuth String email,
                                                                 @RequestBody @Valid final DelegateLeaderDto request){
        return ResponseEntity.ok().body(teamService.delegateLeader(email, request));
    }

    @Operation(summary = "Search Groups", description = "그룹 조회")
    @GetMapping
    public ResponseEntity<List<SearchGroupResponse>> searchGroup(@JwtAuth String email) {
        return ResponseEntity.ok().body(teamService.searchGroup(email));
    }
}
