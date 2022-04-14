package promisor.promisor.domain.team.api;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promisor.promisor.domain.team.dto.ChangeTeamNameResponse;
import promisor.promisor.domain.team.dto.CreateTeamDto;
import promisor.promisor.domain.team.dto.EditTeamDto;
import promisor.promisor.domain.team.dto.GetMyTeamResponse;
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


//    @PatchMapping("/leave/{groupId}")
//    public ResponseEntity<> leaveGroup(){
//        teamService.
//    }

    @GetMapping
    public ResponseEntity<List<GetMyTeamResponse>> getMyGroups(@JwtAuth String email) {
        List<GetMyTeamResponse> response = teamService.getGroupList(email);
        return ResponseEntity.ok().body(response);
    }

}
