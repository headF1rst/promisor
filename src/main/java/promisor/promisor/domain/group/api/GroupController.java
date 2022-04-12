package promisor.promisor.domain.group.api;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import promisor.promisor.domain.group.dto.CreateGroupDto;
import promisor.promisor.domain.group.dto.EditGroupDto;
import promisor.promisor.domain.group.service.GroupService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Create group", description = "그룹 생성")
    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody @Valid final CreateGroupDto request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/groups").toUriString());
        groupService.createGroup(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Edit group name", description = "그룹 이름 수정")
    @PatchMapping
    public ResponseEntity<Void> editGroup(@RequestBody @Valid final EditGroupDto request) {
        groupService.editGroup(request);
        return ResponseEntity.ok().build();
    }


//    @PatchMapping("/leave/{groupId}")
//    public ResponseEntity<> leaveGroup(){
//        teamService.
//    }

}
