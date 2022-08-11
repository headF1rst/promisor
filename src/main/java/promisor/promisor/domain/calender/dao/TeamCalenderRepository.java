package promisor.promisor.domain.calender.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.calender.domain.TeamCalender;
import promisor.promisor.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TeamCalenderRepository extends JpaRepository<TeamCalender, Long> {

    @Query("select tc from TeamCalender tc where tc.personalCalender.id=:id")
    Optional<List<TeamCalender>> getByBanDateId(@Param("id") Long id);

    Slice<TeamCalender> findAllByTeamId(Long id, Pageable pageable);

    @Query("select tc from TeamCalender tc where tc.team.id=:id and tc.date=:date")
    Slice<TeamCalender> findAllByTeamIdAndDate(Long id, LocalDate date, Pageable pageable);

    @Query("select tc from TeamCalender tc where tc.team.id=:id and tc.date=:date")
    List<TeamCalender> findAllByTeamIdAndDates(Long id, LocalDate date);
  
    @Query("select tc from TeamCalender tc " +
            "join fetch tc.member where tc.member = :member and " +
            "function('date_format', tc.date,'%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d')")
    List<TeamCalender> findAllByMemberAndDate(@Param("member")Member member, @Param("date")String date);
}
