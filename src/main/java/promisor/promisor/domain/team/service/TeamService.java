package promisor.promisor.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.dto.CreateGroupDto;
import promisor.promisor.domain.team.dto.EditGroupDto;
import promisor.promisor.domain.team.exception.GroupIdNotFound;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void createGroup(CreateGroupDto request) {
        teamRepository.save(new Team(request.getGroupName()));
    }

    public Team getGroup(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        Team group = optionalTeam.orElseThrow(GroupIdNotFound::new);
        return group;
    }
    @Transactional
    public void editGroup(EditGroupDto request) {
        Team group = getGroup(request.getGroupId());
        group.EditGroupName(request.getGroupName());
    }
}
