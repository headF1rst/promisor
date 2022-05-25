package promisor.promisor.domain.bandate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.dao.PersonalBanDateRepository;
import promisor.promisor.domain.bandate.dao.TeamBanDateRepository;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.bandate.domain.TeamBanDate;
import promisor.promisor.domain.bandate.dto.PersonalBanDateStatusEditRequest;
import promisor.promisor.domain.bandate.dto.RegisterPersonalBanDateResponse;
import promisor.promisor.domain.member.dao.MemberRepository;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.member.exception.MemberNotFoundException;
import promisor.promisor.domain.model.BaseEntity;
import promisor.promisor.domain.team.dao.TeamRepository;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.service.TeamService;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BanDateService {
    private final PersonalBanDateRepository personalBanDateRepository;
    private final MemberRepository memberRepository;
    private final TeamBanDateRepository teamBanDateRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    @Transactional
    public RegisterPersonalBanDateResponse registerPersonal(String email, Date date, String reason) {

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        PersonalBanDate pbd = new PersonalBanDate(member,date,reason);
        personalBanDateRepository.save(pbd);
        List<Team> teams = teamRepository.findGroupInfoWithMembers(member.getId());
        for(int i=0;i<teams.stream().map(BaseEntity::getId).collect(Collectors.toList()).size();i++){
            TeamBanDate tbd = new TeamBanDate(teams.get(i),member,pbd,pbd.getDate(),pbd.getDateStatus());
            teamBanDateRepository.save(tbd);
        }
        return new RegisterPersonalBanDateResponse(pbd.getMember().getId(), pbd.getDate(), pbd.getReason());
    }
    public PersonalBanDate findById(Long id) {
        Optional<PersonalBanDate> optionalPersonalBanDate = personalBanDateRepository.findById(id);
        return optionalPersonalBanDate.orElseThrow();
    }


    public List<TeamBanDate> findByBanDateId(Long id) {
        Optional<List<TeamBanDate>> optionalList = teamBanDateRepository.getByBanDateId(id);
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
}
