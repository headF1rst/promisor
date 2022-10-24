package promisor.promisor.domain.calendar.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.calendar.domain.PersonalCalendar;
import promisor.promisor.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface PersonalCalendarRepository extends JpaRepository<PersonalCalendar, Long> {

    /**
     *
     * 인자로 주어진 멤버와 일시에 해당하는 personalCalendar 객체를 가져온다.
     */
    @Transactional
    @Query("select pc from PersonalCalendar pc " +
            "join fetch pc.member where pc.member = :member and " +
            "function('date_format', pc.date,'%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d')")
    PersonalCalendar findPersonalCalendarByMemberAndDate(@Param("member") Member member, @Param("date") String date);

    @Transactional(readOnly = true)
    Slice<PersonalCalendar> findAllByMemberId(Long id, Pageable pageable);

    @Query("select pc from PersonalCalendar pc " +
            "join fetch pc.member where pc.member = :member and pc.status = 'ACTIVE'")
    List<PersonalCalendar> findAllByMember(@Param("member") Member member);

    @Modifying
    @Query("update PersonalCalendar pc" +
            " set pc.date = :date" +
            " where pc.member = :member")
    void updatePromiseDate(@Param("member") Member member, @Param("date") LocalDate date);
}
