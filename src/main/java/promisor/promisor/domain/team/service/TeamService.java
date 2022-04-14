package promisor.promisor.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.team.dao.TeamMemberRepository;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;
import promisor.promisor.domain.team.dto.ChangeTeamNameResponse;
import promisor.promisor.domain.team.dto.CreateTeamDto;
import promisor.promisor.domain.team.dto.EditTeamDto;
import promisor.promisor.domain.team.dto.GetMyTeamResponse;
import promisor.promisor.domain.team.exception.TeamIdNotFound;
import promisor.promisor.domain.team.exception.NoRightsException;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public void createGroup(String email, CreateTeamDto request) {
        Member member =getMemberInfo(email);
        Team team = teamRepository.save(new Team(member, request.getGroupName()));
        teamMemberRepository.save(new TeamMember(member, team));
    }

    public Member getMemberInfo(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(MemberEmailNotFound::new);
        return member;
    }

    public Team getGroup(Long id) {
        Optional<Team> optionalGroup = teamRepository.findById(id);
        Team team = optionalGroup.orElseThrow(TeamIdNotFound::new);
        return team;
    }

    @Transactional
    public ChangeTeamNameResponse editGroup(String email, EditTeamDto request) {
        Member member = getMemberInfo(email);
        Team team = getGroup(request.getGroupId());
        Member leader = team.getMember();
        if (leader.getId() != member.getId()){
            throw new NoRightsException();
        }
        team.changeGroupName(request.getGroupName());
        return new ChangeTeamNameResponse(team.getGroupName());
    }

    public List<GetMyTeamResponse> getGroupList(String email) {
        Member member = getMemberInfo(email);
        return teamRepository.findMemberGroups(member.getId());
    }

}
