package promisor.promisor.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;
import promisor.promisor.domain.member.exception.MemberNotFoundException;
import promisor.promisor.domain.team.dao.InviteRepository;
import promisor.promisor.domain.team.dao.TeamMemberRepository;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Invite;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;
import promisor.promisor.domain.team.dto.*;
import promisor.promisor.domain.team.exception.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final InviteRepository inviteRepository;

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
        Team team = optionalGroup.orElseThrow(TeamIdNotFoundException::new);
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

    @Transactional
    public LeaveTeamResponse leaveGroup(String email, Long groupId) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(MemberNotFoundException::new);
        Team team = getGroup(groupId);
        Member leader = team.getMember();
        if(leader.getId() == member.getId()){
            throw new LeaderLeaveException();
        }
        teamMemberRepository.leaveGroup(member, team);
        return new LeaveTeamResponse(
                member.getId(),
                groupId
        );
    }

    @Transactional
    public InviteTeamResponse inviteGroup(String email, InviteTeamDto request){

        Member inviting = getMemberInfo(email);
        Member invited = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Team team = getGroup(request.getGroupId());
        if(!Objects.equals(team.getMember().getId(), inviting.getId())){
            throw new NoRightToInviteException();
        }

        inviteRepository.save(new Invite(invited,team, '0'));
        return new InviteTeamResponse(
                invited.getId(), team.getId()
        );
    }

    @Transactional
    public DelegateLeaderResponse delegateLeader(String email, DelegateLeaderDto request) {

        Member oldLeader = getMemberInfo(email);
        Member newLeader = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Team team = getGroup(request.getGroupId());
        if(!Objects.equals(team.getMember().getId(), oldLeader.getId())){
            throw new NoRightToDelegateException();
        }
        team.changeLeader(newLeader);
        return new DelegateLeaderResponse(request.getMemberId(), request.getGroupId());
    }

    /*
     *   그룹 조회 API
     *   @Param: 조회 요청자 이메일
     *   @Return: 조회 그룹 정보
     *   @author: Sanha Ko
     */
    public List<SearchGroupResponse> searchGroup(String email) {
        Member member = getMemberInfo(email);
        return teamRepository.findAllWithMember(member.getId());
    }
}
