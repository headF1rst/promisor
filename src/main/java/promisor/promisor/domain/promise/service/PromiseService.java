package promisor.promisor.domain.promise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.domain.MemberRole;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;
import promisor.promisor.domain.promise.dao.PromiseRepository;
import promisor.promisor.domain.promise.domain.Promise;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.exception.TeamNotFoundForMember;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PromiseService {

    private final PromiseRepository promiseRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void createPromise(String email, String name) {

        Member leader = getMember(email);
        leader.changeRole(MemberRole.valueOf("LEADER"));
        Team team = getTeam(leader);
        Promise.of("ACTIVE", team, name);
    }

    public Member getMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(MemberEmailNotFound::new);
    }

    public Team getTeam(Member member) {
        Optional<Team> optionalTeam = teamRepository.findByMember(member);
        return optionalTeam.orElseThrow(TeamNotFoundForMember::new);
    }
}
