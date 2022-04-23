package promisor.promisor.domain.team.api;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.team.dto.*;
import promisor.promisor.domain.team.service.TeamService;
import promisor.promisor.global.auth.JwtAuth;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "Create group", description = "그룹 생성")
    @PostMapping
    public ResponseEntity<Void> createGroup(@JwtAuth String email,
                                            @RequestBody @Valid final CreateTeamDto request) {
        teamService.createGroup(email, request);
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

//    @GetMapping
//    public ResponseEntity<List<GetMyTeamResponse>> getMyGroups(@JwtAuth String email) {
//        List<GetMyTeamResponse> response = teamService.getGroupList(email);
//        return ResponseEntity.ok().body(response);
//    }

}
