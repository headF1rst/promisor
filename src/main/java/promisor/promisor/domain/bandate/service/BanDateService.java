package promisor.promisor.domain.bandate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.dao.PersonalBanDateRepository;
import promisor.promisor.domain.bandate.dao.TeamBanDateRepository;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.bandate.domain.TeamBanDate;
import promisor.promisor.domain.bandate.dto.GetTeamCalendarResponse;
import promisor.promisor.domain.bandate.dto.PersonalBanDateStatusEditRequest;
import promisor.promisor.domain.bandate.dto.RegisterPersonalBanDateResponse;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BanDateService {
    private final PersonalBanDateRepository personalBanDateRepository;
    private final MemberRepository memberRepository;
    private final TeamBanDateRepository teamBadDateRepository;

    @Transactional
    public RegisterPersonalBanDateResponse registerPersonal(String email, Date date, String reason) {

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        PersonalBanDate pbd = new PersonalBanDate(member,date,reason);
        personalBanDateRepository.save(pbd);
        return new RegisterPersonalBanDateResponse(pbd.getMember().getId(), pbd.getDate(), pbd.getReason());
    }

    public PersonalBanDate findById(Long id) {
        Optional<PersonalBanDate> optionalPersonalBanDate = personalBanDateRepository.findById(id);
        return optionalPersonalBanDate.orElseThrow();
    }


    public List<TeamBanDate> findByBanDateId(Long id) {
        Optional<List<TeamBanDate>> optionalList = teamBadDateRepository.getByBanDateId(id);
        return optionalList.orElseThrow();
    }

    @Transactional
    public void editPersonalBanDateStatus(String email, PersonalBanDateStatusEditRequest request) {
        PersonalBanDate pdb = findById(request.getId());
        pdb.editPBDStatus(request.getStatus());
        List<TeamBanDate> pbdList = findByBanDateId(pdb.getId());
        for (int i=0; i<pbdList.size(); i++) {
            pbdList.get(i).editTBDStatus(request.getStatus());
        }
    }

    public List<GetTeamCalendarResponse> getTeamCalendar(String email, Long teamId) {
        PageRequest pageRequest = PageRequest.of(0, 31, Sort.by(Sort.Direction.ASC, "date"));
        Slice<TeamBanDate> teamCalendarList = teamBadDateRepository.findAllByTeamId(teamId, pageRequest);

        List<GetTeamCalendarResponse> result = teamCalendarList.stream()
                .map(p -> new GetTeamCalendarResponse(p.getId(), p.getMember().getId(), p.getMember().getName(),
                        p.getPersonalBanDate().getId(), p.getDate(), p.getDateStatus()))
                .collect(toList());
        return result;
    }
}
