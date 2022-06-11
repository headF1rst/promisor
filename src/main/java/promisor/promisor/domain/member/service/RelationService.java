package promisor.promisor.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.dao.RelationRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.dto.response.MemberResponse;
import promisor.promisor.domain.member.exception.EmailEmptyException;
import promisor.promisor.domain.member.exception.ExistFriendException;
import promisor.promisor.domain.member.exception.MemberEmailNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;

    private Member getMember(String email) {
        log.info("Fetching member '{}'", email);
        return memberRepository.findByEmail(email).orElseThrow(MemberEmailNotFound::new);
    }

    private Member getMemberById(Long id) {
        log.info("Fetching member '{}'", id);
        return memberRepository.findById(id).orElseThrow(MemberEmailNotFound::new);
    }

    /*
     *   친구 요청 API
     *   @Param: 친구 요청자 이메일, 수락자 id
     *   @author: Sanha Ko
     */
    @Transactional
    public void followFriend(String email, Long friendId) {

        Member requester = getMember(email);
        Member receiver = getMemberById(friendId);

        log.info("requester: '{}', receiver: '{}'", requester, receiver);

        if (requester.hasFriend(receiver) || relationRepository.existsByOwnerEmailAndFriendEmail(email, receiver.getEmail())) {
            throw new ExistFriendException(receiver.getEmail());
        }
        requester.addFriend(receiver);
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
        Member receiver = getMember(findEmail);
        return new MemberResponse(receiver);
    }

    /*
     *   친구 삭제 API
     *   @Param: 요청자 이메일, 대상자 id
     *   @author: Sanha Ko
     */
    @Transactional
    public void deleteFriend(String email, Long friendId) {

        Member member = getMember(email);
        Member friend = getMemberById(friendId);
        member.deleteFriend(friend);
    }

    /*
     *   친구 전체 조회 API
     *   @Param: 이메일
     *   @author: Sanha Ko
     */
    public List<MemberResponse> getFriendList(String email) {

        Member member = getMember(email);
        return member.getMemberFriends().stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
    }
}
