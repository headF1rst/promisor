package promisor.promisor.domain.calender.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.calender.domain.PersonalCalender;
import promisor.promisor.domain.calender.domain.PersonalSchedule;

import java.util.List;

@Transactional(readOnly = true)
public interface PersonalScheduleRepository extends JpaRepository<PersonalSchedule, Long>{

    @Query(value = "select * from personal_shedule ps join personal_calender pc on ps.personal_calender_id = pc.id\n" +
            "join member m on pc.member_id = m.id\n" +
            "where pc.member_id = :memberId",
    nativeQuery = true)
    List<PersonalSchedule> findAllByMember(@Param("memberId") Long id);

    @Query("select ps from PersonalSchedule ps join ps.personalCalender pc where pc = :pc")
    List<PersonalSchedule> findAllByPersonalCalender(@Param("pc") PersonalCalender pc);
}
