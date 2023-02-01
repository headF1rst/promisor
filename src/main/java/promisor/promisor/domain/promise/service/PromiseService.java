package promisor.promisor.domain.promise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import promisor.promisor.domain.calendar.dao.PersonalCalendarRepository;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;
import promisor.promisor.domain.promise.dao.PromiseRepository;
import promisor.promisor.domain.promise.domain.Promise;
import promisor.promisor.domain.promise.dto.request.PromiseCreateRequest;
import promisor.promisor.domain.promise.dto.request.PromiseDateEditRequest;
import promisor.promisor.domain.promise.dto.response.PromiseResponse;
import promisor.promisor.domain.promise.exception.MemberNotBelongsToTeam;
import promisor.promisor.domain.team.dao.TeamMemberRepository;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;
import promisor.promisor.domain.promise.exception.PromiseIdNotFound;
import promisor.promisor.domain.team.exception.TeamNotFoundForMember;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PromiseService {

    private final PromiseRepository promiseRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    private final PersonalCalendarRepository personalBanDateRepository;

    /*
     *   약속 생성 API
     *   @Param: 이메일, 팀 이름, 팀 아이디
     *   @author: Sanha Ko
     */
    @Transactional
    public void createPromise(String email, PromiseCreateRequest request, Long teamId) {
        Team team = getTeamById(teamId);
        if (checkMemberInTeam(email, team)) {
            throw new MemberNotBelongsToTeam();
        }
        Promise promise = Promise.of("ACTIVE", team, request.getName());
        promiseRepository.save(promise);
    }

    /**
     * 그룹내 약속 수정 API
     * @param email
     * @param request
     * @param promiseId
     * @author Sanha Ko
     */
    @Transactional
    public void editPromise(String email, PromiseDateEditRequest request, Long promiseId) {
        Assert.notNull(email, "엑세스 토큰이 전달되지 않았습니다.");
        Assert.notNull(promiseId, "약속 Id가 존재하지 않습니다.");

        Promise promise = getPromiseById(promiseId);

        if (!request.getDate().isEmpty()) {
            List<TeamMember> members = teamMemberRepository.findTeamMembersByTeam(promise.getTeam());

            for (TeamMember teamMember : members) {
                promiseRepository.updateBandDateOfMembers(request.getName(),
                        teamMember.getMember(), promise.getDate());
            }
        }
        promise.changePromiseDate(request.getDate());
        promise.changePromiseContent(request.getName(), request.getLocation());
    }

    /**
     * 그룹내 약속 전체 조회 API
     * @param email
     * @param teamId
     * @return
     * @author Sanha Ko
     */
    public List<PromiseResponse> searchPromises(String email, Long teamId) {

        Team team = getTeamById(teamId);
        if (checkMemberInTeam(email, team)) {
            throw new MemberNotBelongsToTeam();
        }

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<Promise> promiseList = promiseRepository.findAllByTeamId(teamId, pageRequest);

        return promiseList.stream()
                .map(p -> new PromiseResponse(p.getId(), p.getPromiseName(),
                        p.getDate(), p.getPromiseLocation()))
                .collect(toList());
    }

    public PromiseResponse searchPromise(String email, Long promiseId) {
        Assert.notNull(email, "엑세스 토큰이 전달되지 않았습니다.");
        Assert.notNull(promiseId, "약속 Id가 존재하지 않습니다.");

        Promise promise = getPromiseById(promiseId);
        return new PromiseResponse(promise.getId(), promise.getPromiseName(),
                promise.getDate(), promise.getPromiseLocation());
    }

    private Member getMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberEmailNotFound::new);
    }

    private Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(TeamNotFoundForMember::new);
    }

    private Promise getPromiseById(Long id) {
        return promiseRepository.findById(id).orElseThrow(PromiseIdNotFound::new);
    }

    private boolean checkMemberInTeam(String email, Team team) {

        List<TeamMember> foundMembers = teamMemberRepository.findTeamMembersByTeam(team);
        Member member = getMember(email);

        for (TeamMember foundMember : foundMembers) {
            if (foundMember.getMember() == member) {
                return false;
            }
        }
        return true;
    }
}
