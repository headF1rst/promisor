package promisor.promisor.domain.calendar.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.calendar.domain.TeamCalendar;
import promisor.promisor.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TeamCalendarRepository extends JpaRepository<TeamCalendar, Long> {

    @Query("select tc from TeamCalendar tc where tc.personalCalendar.id=:id")
    Optional<List<TeamCalendar>> getByBanDateId(@Param("id") Long id);

    Slice<TeamCalendar> findAllByTeamId(Long id, Pageable pageable);

    @Query("select tc from TeamCalendar tc where tc.team.id=:id and tc.date=:date")
    Slice<TeamCalendar> findAllByTeamIdAndDate(Long id, LocalDate date, Pageable pageable);

    @Query("select tc from TeamCalendar tc where tc.team.id=:id and tc.date=:date")
    List<TeamCalendar> findAllByTeamIdAndDates(Long id, LocalDate date);
  
    @Query("select tc from TeamCalendar tc " +
            "join fetch tc.member where tc.member = :member and " +
            "function('date_format', tc.date,'%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d')")
    List<TeamCalendar> findAllByMemberAndDate(@Param("member")Member member, @Param("date")String date);
}
