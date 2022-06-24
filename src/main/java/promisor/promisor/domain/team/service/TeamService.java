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
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;
import promisor.promisor.domain.team.dto.request.*;
import promisor.promisor.domain.team.dto.response.*;
import promisor.promisor.domain.team.exception.*;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    /*
     *   그룹 생성 API
     *   @Param: 생성자 이메일, 그룹 이름
     *   @author: Sanha Ko
     */
    @Transactional
    public Long createGroup(String email, CreateTeamRequest request) {

        Member member = getMemberByEmail(email);
        Team team = teamRepository.save(new Team(member, request.getGroupName()));
        teamMemberRepository.save(new TeamMember(member, team));
        return team.getId();
    }

    @Transactional
    public ChangeTeamNameResponse editGroup(String email, EditTeamRequest request) {

        Member member = getMemberByEmail(email);
        Team team = getGroupById(request.getGroupId());
        Member leader = team.getMember();
        if (leader.getId() != member.getId()){
            throw new NoRightsException();
        }
        team.changeGroupName(request.getGroupName());
        return new ChangeTeamNameResponse(team.getGroupName());
    }

    @Transactional
    public LeaveTeamResponse leaveGroup(String email, Long groupId) {

        Member member = getMemberByEmail(email);
        Team team = getGroupById(groupId);
        Member leader = team.getMember();

        if(leader.getId() == member.getId()) {
            throw new LeaderLeaveException();
        }
        teamMemberRepository.leaveGroup(member, team);
        return new LeaveTeamResponse(
                member.getId(),
                groupId
        );
    }

    @Transactional
    public InviteTeamResponse inviteGroup(String email, InviteTeamRequest request){

        Member inviting = getMemberByEmail(email);
        Team team = getGroupById(request.getGroupId());

        if(!Objects.equals(team.getMember().getId(), inviting.getId())){
            throw new NoRightToInviteException();
        }

        Member[] invited = new Member[request.getMemberId().length];
        for(int i = 0; i < request.getMemberId().length; i++) {
            invited[i] = memberRepository.findById(request.getMemberId()[i]).orElseThrow(MemberNotFoundException::new);
            TeamMember teamMember = teamMemberRepository.save(new TeamMember(invited[i], team));
            team.addTeam(teamMember);
        }
        return new InviteTeamResponse(
                team.getId()
        );
    }

    @Transactional
    public DelegateLeaderResponse delegateLeader(String email, DelegateLeaderRequest request) {

        Member oldLeader = getMemberByEmail(email);
        Member newLeader = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Team team = getGroupById(request.getGroupId());
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

        Member member = getMemberByEmail(email);
        List<TeamMember> teams = teamRepository.findGroupInfoWithMembers(member);
        return teams.stream()
                .map(m -> new SearchGroupResponse(m.getTeamIdFromTeam(), m.getGroupNameFromTeam(),
                        m.getImageUrlFromTeam(), m.getTeamMembersFromTeam()))
                .collect(toList());
    }

    @Transactional
    public EditMyLocationResponse editMyLocation(String email, EditMyLocationRequest request) {

        Member member = getMemberByEmail(email);
        TeamMember teamMember = teamMemberRepository.findMemberByMemberIdAndTeamId(member.getId(), request.getTeamId());
        teamMember.editMyLocation(request.getLatitude(), request.getLongitude());
        return new EditMyLocationResponse(request.getLatitude(), request.getLongitude());
    }

    public GetMidPointResponse getMidPoint(String email, Long teamId) {

        double avgLatitude = 0;
        double avgLongitude = 0;

        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }

        List<TeamMember> teamMemberList = teamMemberRepository.findMembersByTeamId(teamId);

        for (TeamMember teamMember : teamMemberList) {
            avgLatitude = avgLatitude + teamMember.getLatitude();
            avgLongitude = avgLongitude + teamMember.getLongitude();
        }
        avgLatitude = avgLatitude / teamMemberList.size();
        avgLongitude = avgLongitude / teamMemberList.size();
        return new GetMidPointResponse(teamId, avgLatitude, avgLongitude);
    }

    public List<GetTeamMembersLocationResponse> getTeamMembersLocation(String email, Long teamId) {
        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }
        List<TeamMember> teamMemberList = teamMemberRepository.findMembersByTeamId(teamId);
        return teamMemberList.stream()
                .map(m -> new GetTeamMembersLocationResponse(m.getTeam().getId(), m.getMember().getId(),
                        m.getMember().getName(), m.getLatitude(), m.getLongitude()))
                .collect(toList());
    }

    private Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(MemberEmailNotFound::new);
    }

    private Team getGroupById(Long id) {
        return teamRepository.findById(id).orElseThrow(TeamIdNotFoundException::new);
    }

    public boolean checkMemberInTeam(String email, Long teamId) {

        List<TeamMember> foundMembers = teamMemberRepository.findMembersByTeamId(teamId);
        Member member = getMemberByEmail(email);
        for (TeamMember foundMember : foundMembers) {
            if (foundMember.getMember() == member) {
                return false;
            }
        }
        return true;
    }
}
