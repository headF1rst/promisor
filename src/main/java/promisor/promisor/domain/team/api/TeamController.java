package promisor.promisor.domain.team.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import promisor.promisor.domain.team.dto.CreateGroupDto;
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

}
