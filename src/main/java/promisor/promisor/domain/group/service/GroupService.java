package promisor.promisor.domain.group.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.group.dao.GroupRepository;
import promisor.promisor.domain.group.domain.Group;
import promisor.promisor.domain.group.dto.CreateGroupDto;
import promisor.promisor.domain.group.dto.EditGroupDto;
import promisor.promisor.domain.group.exception.GroupIdNotFound;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public void createGroup(CreateGroupDto request) {
        groupRepository.save(new Group(request.getGroupName()));
    }

    public Group getGroup(Long id) {
        Optional<Group> optionalTeam = groupRepository.findById(id);
        Group group = optionalTeam.orElseThrow(GroupIdNotFound::new);
        return group;
    }
    @Transactional
    public void editGroup(EditGroupDto request) {
        Group group = getGroup(request.getGroupId());
        group.changeGroupName(request.getGroupName());
    }
}
