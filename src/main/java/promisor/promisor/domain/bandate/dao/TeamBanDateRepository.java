package promisor.promisor.domain.bandate.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.bandate.domain.TeamBanDate;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Team;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TeamBanDateRepository extends JpaRepository<TeamBanDate, Long> {

    @Query("select tbd from TeamBanDate tbd where tbd.personalBanDate.id=:id")
    Optional<List<TeamBanDate>> getByBanDateId(@Param("id") Long id);

    Slice<TeamBanDate> findAllByTeamId(Long id, Pageable pageable);

    @Query("select tbd from TeamBanDate tbd where tbd.team.id=:id and tbd.date=:date")
    Slice<TeamBanDate> findAllByTeamIdAndDate(Long id, LocalDate date, Pageable pageable);

    @Query("select tbd from TeamBanDate tbd where tbd.team.id=:id and tbd.date=:date")
    List<TeamBanDate> findAllByTeamIdAndDates(Long id, LocalDate date);
  
    @Query("select tbd from TeamBanDate tbd " +
            "join fetch tbd.member where tbd.member = :member and " +
            "function('date_format',tbd.date,'%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d')")
    List<TeamBanDate> findAllByMemberAndDate(@Param("member")Member member, @Param("date")String date);
}
