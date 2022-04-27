package promisor.promisor.domain.promise.service;

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
import promisor.promisor.domain.promise.dao.PromiseRepository;
import promisor.promisor.domain.promise.domain.Promise;
import promisor.promisor.domain.promise.dto.PromiseCreateRequest;
import promisor.promisor.domain.promise.dto.PromiseDateEditRequest;
import promisor.promisor.domain.promise.dto.PromiseResponse;
import promisor.promisor.domain.promise.exception.MemberNotBelongsToTeam;
import promisor.promisor.domain.team.dao.TeamMemberRepository;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;
import promisor.promisor.domain.team.exception.NoRightsException;
import promisor.promisor.domain.promise.exception.PromiseIdNotFound;
import promisor.promisor.domain.team.exception.TeamNotFoundForMember;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    /*
     *   약속 생성 API
     *   @Param: 이메일, 팀 이름, 팀 아이디
     *   @author: Sanha Ko
     */
    @Transactional
    public void createPromise(String email, PromiseCreateRequest request, Long teamId) {

        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }

        Team team = getTeamById(teamId);
        Promise promise = Promise.of("ACTIVE", team, request.getName());
        promiseRepository.save(promise);
    }

    @Transactional
    public void editPromiseDate(String email, PromiseDateEditRequest request) {
        Promise promise = getPromiseById(request.getPromiseId());
        promise.editPromiseDate(request.getDate());
    }

    /*
     *   그룹내 약속 전체 조회 API
     *   @Param: 이메일, 팀 아이디
     *   @author: Sanha Ko
     */
    public List<PromiseResponse> searchPromise(String email, Long teamId) {

        if (checkMemberInTeam(email, teamId)) {
            throw new MemberNotBelongsToTeam();
        }

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "promiseName"));
        Slice<Promise> promiseList = promiseRepository.findAllByTeamId(teamId, pageRequest);

        List<PromiseResponse> result = promiseList.stream()
                .map(p -> new PromiseResponse(p.getId(), p.getPromiseName(),
                        p.getDate(), p.getPromiseLocation()))
                .collect(toList());
        return result;
    }

    public Member getMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(MemberEmailNotFound::new);
    }

    public Team getTeamById(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        return optionalTeam.orElseThrow(TeamNotFoundForMember::new);
    }

    public Promise getPromiseById(Long id) {
        Optional<Promise> optionalPromise = promiseRepository.findById(id);
        return optionalPromise.orElseThrow(PromiseIdNotFound::new);
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
}
