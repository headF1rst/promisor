package promisor.promisor.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.dao.RelationRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.domain.Relation;
import promisor.promisor.domain.member.dto.MemberResponse;
import promisor.promisor.domain.member.exception.EmailEmptyException;
import promisor.promisor.domain.member.exception.ExistFriendException;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    public Member getMember(String email) {
        log.info("Fetching member '{}'", email);
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(MemberEmailNotFound::new);
        return member;
    }

    /*
     *   친구 요청 API
     *   @Param: 친구 요청자 이메일, 수락자 id
     *   @author: Sanha Ko
     */
    @Transactional
    public void followFriend(String email, Long friendId) {

        Member requester = getMember(email);

        Optional<Member> optionalReceiver  = memberRepository.findById(friendId);
        Member receiver = optionalReceiver.orElseThrow(MemberEmailNotFound::new);

        log.info("requester: '{}', receiver: '{}'", requester, receiver);

        if (requester.hasFriend(receiver) || relationRepository.existsByOwnerEmailAndFriendEmail(email, receiver.getEmail())) {
            throw new ExistFriendException(receiver.getEmail());
        }
        relationRepository.save(new Relation(requester, receiver, "INACTIVE"));
    }

    /*
     *   친구 검색 API
     *   @Param: 조회 요청자 이메일, 조회 대상자 이메일
     *   @Return: 조회 대상자 정보
     *   @author: Sanha Ko
     */
    public MemberResponse searchFriend(String email, String findEmail) {

        if (findEmail.isBlank()) {
            throw new EmailEmptyException();
        }
        Member requester = getMember(email);
        Member receiver = getMember(findEmail);

        return new MemberResponse(receiver);
    }
}
