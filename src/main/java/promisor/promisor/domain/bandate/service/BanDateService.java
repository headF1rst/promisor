package promisor.promisor.domain.bandate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.dao.PersonalBanDateRepository;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.bandate.dto.RegisterPersonalBanDateResponse;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberNotFoundException;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BanDateService {
    private final PersonalBanDateRepository personalBanDateRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RegisterPersonalBanDateResponse registerPersonal(String email, Date date, String reason) {

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        PersonalBanDate pbd = new PersonalBanDate(member,date,reason);
        personalBanDateRepository.save(pbd);
        return new RegisterPersonalBanDateResponse(pbd.getMember().getId(), pbd.getDate(), pbd.getReason());
    }
}
