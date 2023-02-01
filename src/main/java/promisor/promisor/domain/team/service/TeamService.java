package promisor.promisor.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;
import promisor.promisor.domain.member.exception.MemberNotFoundException;
import promisor.promisor.domain.promise.exception.MemberNotBelongsToTeam;
import promisor.promisor.domain.team.dao.TeamMemberRepository;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;
import promisor.promisor.domain.team.dto.request.*;
import promisor.promisor.domain.team.dto.response.*;
import promisor.promisor.domain.team.exception.*;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public Long createGroup(String email, CreateTeamRequest request) {

        Member member = getMemberByEmail(email);
        Team team = teamRepository.save(new Team(member.getId(), request.getGroupName()));
        teamMemberRepository.save(new TeamMember(member, team));
        return team.getId();
    }

    @Transactional
    public ChangeTeamNameResponse editGroup(String email, EditTeamRequest request) {

        Member member = getMemberByEmail(email);
        Team team = getTeamById(request.getGroupId());

        if (member.isNotLeader(team.getTeamLeaderId())) {
            throw new IllegalArgumentException(String.format("회원 Id = %s와 그룹장 Id = %s가 일치하지 않습니다.",
                    member.getId(), team.getTeamLeaderId()));
        }
        team.changeGroupName(request.getGroupName());
        return new ChangeTeamNameResponse(team.getTeamName());
    }

    @Transactional
    public LeaveTeamResponse leaveGroup(String email, Long groupId) {

        Member member = getMemberByEmail(email);
        Team team = getTeamById(groupId);

        if (member.isLeader(team.getTeamLeaderId())) {
            throw new IllegalArgumentException("그룹내 리더는 그룹을 탈퇴할 수 없습니다. 리더를 위임해 주세요.");
        }
        teamMemberRepository.leaveGroup(member, team);
        return new LeaveTeamResponse(member.getId(), groupId);
    }

    @Transactional
    public InviteTeamResponse inviteGroup(String email, InviteTeamRequest request){

        Member invitee = getMemberByEmail(email);
        Team team = getTeamById(request.getTeamId());

        if (team.memberNotBelongsToTeam(invitee.getId())) {
            throw new IllegalArgumentException(String.format("초대자(%s)가 그룹에 속해있지 않는 멤버입니다.", invitee.getId()));
        }

        for (Long teamMemberId : request.getMemberId()) {
            Member member = memberRepository.findById(teamMemberId).orElseThrow(MemberNotFoundException::new);
            TeamMember teamMember = teamMemberRepository.save(new TeamMember(member, team));
            team.addTeam(teamMember);
        }
        return new InviteTeamResponse(team.getId());
    }

    @Transactional
    public DelegateLeaderResponse delegateLeader(String email, DelegateLeaderRequest request) {

        Member prevLeader = getMemberByEmail(email);
        Member postLeader = memberRepository.findById(request.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Team team = getTeamById(request.getTeamId());

        if (team.memberNotBelongsToTeam(prevLeader.getId())) {
            throw new IllegalArgumentException(
                    String.format("기존 리더(%s)가 그룹에 속해있지 않는 멤버입니다.", prevLeader.getId()));
        }
        team.changeLeader(postLeader);
        return new DelegateLeaderResponse(request.getMemberId(), request.getTeamId());
    }

    public List<SearchGroupResponse> searchGroup(String email) {

        Member member = getMemberByEmail(email);

        PageRequest pageRequest = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<TeamMember> teamMembers = teamRepository.findTeamInfoWithMembers(member, pageRequest);
        return teamMembers.stream()
                .map(teamMember -> new SearchGroupResponse(teamMember.getTeamId(), teamMember.getTeamName(),
                        teamMember.getImageUrl(), teamMember.getTeamMembers()))
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

        final int LATITUDE_INDEX = 0;
        final int LONGITUDE_INDEX = 1;

        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        List<Double> points = team.calculateMidPoint();
        return new GetMidPointResponse(teamId, points.get(LATITUDE_INDEX), points.get(LONGITUDE_INDEX));
    }

    public List<GetTeamMembersLocationResponse> getTeamMembersLocation(String email, Long teamId) {

        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }
        Team team = getTeamById(teamId);
        List<TeamMember> teamMembers = teamMemberRepository.findTeamMembersByTeam(team);
        return teamMembers.stream()
                .map(teamMember -> new GetTeamMembersLocationResponse(teamMember.getTeamId(), teamMember.getMemberId(),
                        teamMember.getMemberName(), teamMember.getLatitude(), teamMember.getLongitude()))
                .collect(toList());
    }

    private Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(MemberEmailNotFound::new);
    }

    private Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(TeamIdNotFoundException::new);
    }

    public boolean checkMemberInTeam(String email, Long teamId) {
        Team team = getTeamById(teamId);
        List<TeamMember> foundMembers = teamMemberRepository.findTeamMembersByTeam(team);
        Member member = getMemberByEmail(email);
        for (TeamMember foundMember : foundMembers) {
            if (foundMember.getMember() == member) {
                return false;
            }
        }
        return true;
    }
}
