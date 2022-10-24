package promisor.promisor.domain.promise.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.promise.domain.Promise;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PromiseRepository extends JpaRepository<Promise, Long> {

    @Transactional(readOnly = true)
    Slice<Promise> findAllByTeamId(Long id, Pageable pageable);

    @Modifying
    @Query("update PersonalSchedule pbr" +
            " set pbr.reason = :promiseName" +
            " where pbr.personalBanDate =" +
                " (select pb from PersonalCalendar pb" +
                " left join pb.member" +
                " where pb.member = :member" +
                " and pb.date = :date)")
    void updateBandDateOfMembers(@Param("promiseName") String promiseName,
                                 @Param("member") Member member,
                                 @Param("date") LocalDate date);
}
