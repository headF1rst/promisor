package promisor.promisor.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;
import promisor.promisor.domain.member.exception.MemberNotFoundException;
import promisor.promisor.domain.promise.exception.MemberNotBelongsToTeam;
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

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final InviteRepository inviteRepository;

    /*
     *   그룹 생성 API
     *   @Param: 생성자 이메일, 그룹 이름
     *   @author: Sanha Ko
     */
    @Transactional
    public Long createGroup(String email, CreateTeamDto request) {

        Member member =getMemberInfo(email);
        Team team = teamRepository.save(new Team(member, request.getGroupName()));
        teamMemberRepository.save(new TeamMember(member, team));
        return team.getId();
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
        Team team = getGroup(request.getGroupId());
        if(!Objects.equals(team.getMember().getId(), inviting.getId())){
            throw new NoRightToInviteException();
        }
        Member[] invited = new Member[request.getMemberId().length];
        for(int i=0;i<request.getMemberId().length;i++) {
            invited[i] = memberRepository.findById(request.getMemberId()[i]).orElseThrow(MemberNotFoundException::new);
            inviteRepository.save(new Invite(invited[i],team, '0'));
        }
        return new InviteTeamResponse(
                team.getId()
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

        System.out.println(email);
        Member member = getMemberInfo(email);
        List<Team> teams = teamRepository.findGroupInfoWithMembers(member.getId());
        List<SearchGroupResponse> result = teams.stream()
                .map(m -> new SearchGroupResponse(m.getId(), m.getGroupName(), m.getImageUrl(), m.getTeamMembers()))
                .collect(toList());
        return result;
    }

    @Transactional
    public EditMyLocationResponse editMyLocation(String email, EditMyLocationDto request) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(MemberNotFoundException::new);
        TeamMember teamMember = teamMemberRepository.findMemberByMemberIdAndTeamId(member.getId(), request.getTeamId());
        teamMember.editMyLocation(request.getLatitude(), request.getLongitude());
        return new EditMyLocationResponse(request.getLatitude(), request.getLongitude());
    }

    public GetMidPointResponse getMidPoint(String email, Long teamId) {

        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }
        List<TeamMember> teamMemberList = teamMemberRepository.findMembersByTeamId(teamId);
        float avgLatitude=0;
        float avgLongitude=0;
        for (int i=0; i<teamMemberList.size(); i++) {
            avgLatitude=avgLatitude+teamMemberList.get(i).getLatitude();
            avgLongitude=avgLongitude+teamMemberList.get(i).getLongitude();
        }
        avgLatitude=avgLatitude/teamMemberList.size();
        avgLongitude=avgLongitude/teamMemberList.size();
        return new GetMidPointResponse(teamId, avgLatitude, avgLongitude);
    }

    public boolean checkMemberInTeam(String email, Long teamId) {

        List<TeamMember> foundMembers = teamMemberRepository.findMembersByTeamId(teamId);
        Member member = getMember(email);

        for (TeamMember foundMember : foundMembers) {
            if (foundMember.getMember() == member) {
                return false;
            }
        }
        return true;
    }

    public Member getMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(MemberEmailNotFound::new);
    }
}
