package promisor.promisor.domain.team.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import promisor.promisor.domain.team.dto.CreateGroupDto;
import promisor.promisor.domain.team.dto.EditGroupDto;
import promisor.promisor.domain.team.service.TeamService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody @Valid final CreateGroupDto request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/groups").toUriString());
        teamService.createGroup(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> editGroup(@RequestBody @Valid final EditGroupDto request) {
        teamService.editGroup(request);
        return ResponseEntity.ok().build();
    }



}
