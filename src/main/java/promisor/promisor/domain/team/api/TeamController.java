package promisor.promisor.domain.team.api;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.team.dto.request.*;
import promisor.promisor.domain.team.dto.response.*;
import promisor.promisor.domain.team.exception.TooManyRequestException;
import promisor.promisor.domain.team.service.TeamService;
import promisor.promisor.global.auth.JwtAuth;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class TeamController {

    private final TeamService teamService;
    private final Bucket bucket;


    public TeamController(TeamService teamService) {
        this.teamService = teamService;

        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMillis(30000)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
        }

    @Operation(summary = "Create group", description = "그룹 생성")
    @PostMapping
    public ResponseEntity<Long> createGroup(@JwtAuth String email,
                                            @RequestBody @Valid CreateTeamRequest request) {

        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok().body(teamService.createGroup(email, request));
        }
        throw new TooManyRequestException();
    }

    @Operation(summary = "Edit group name", description = "그룹 이름 수정")
    @PatchMapping
    public ResponseEntity<ChangeTeamNameResponse> editGroup(@JwtAuth String email,
                                                            @RequestBody @Valid EditTeamRequest request) {
        return ResponseEntity.ok().body(teamService.editGroup(email, request));
    }

    @Operation(summary = "Leave group", description = "그룹 탈퇴")
    @PatchMapping("/leave/{groupId}")
    public ResponseEntity<LeaveTeamResponse> leaveGroup(@JwtAuth String email,
                                                        @PathVariable("groupId")Long groupId){
        return ResponseEntity.ok().body(teamService.leaveGroup(email, groupId));
    }

    @Operation(summary = "Invite group", description = "그룹 초대")
    @PostMapping("/invite")
    public ResponseEntity<InviteTeamResponse> inviteGroup(@JwtAuth String email,
                                                          @RequestBody @Valid InviteTeamRequest request){
        return ResponseEntity.ok().body(teamService.inviteGroup(email,request));
    }

    @Operation(summary = "Delegate GroupLeader", description = "그룹장 위임")
    @PatchMapping("/delegate")
    public ResponseEntity<DelegateLeaderResponse> delegateLeader(@JwtAuth String email,
                                                                 @RequestBody @Valid DelegateLeaderRequest request) {
        return ResponseEntity.ok().body(teamService.delegateLeader(email, request));
    }

    @Operation(summary = "Search Groups", description = "그룹 조회")
    @GetMapping
    public ResponseEntity<List<SearchGroupResponse>> searchGroup(@JwtAuth String email) {
        return ResponseEntity.ok().body(teamService.searchGroup(email));
    }

    @Operation(summary = "Edit My Location", description = "그룹 내 자신의 위치 수정")
    @PatchMapping("/location")
    public ResponseEntity<EditMyLocationResponse> editMyLocation(@JwtAuth String email,
                                                                 @RequestBody EditMyLocationRequest request) {
        return ResponseEntity.ok().body(teamService.editMyLocation(email, request));
    }

    @Operation(summary = "Get MidPoint", description = "그룹원들의 중간 지점 조회")
    @GetMapping("/mid-point/{teamId}")
    public ResponseEntity<GetMidPointResponse> getMidPoint(@JwtAuth String email,
                                                           @PathVariable Long teamId) {
        GetMidPointResponse result = teamService.getMidPoint(email, teamId);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Get Team Members Location", description = "그룹원들의 위치 조회")
    @GetMapping("/location/{teamId}")
    public ResponseEntity<List<GetTeamMembersLocationResponse>> getTeamMembersLocation(@JwtAuth String email,
                                                                                       @PathVariable Long teamId) {
        List<GetTeamMembersLocationResponse> result = teamService.getTeamMembersLocation(email, teamId);
        return ResponseEntity.ok().body(result);
    }
}
