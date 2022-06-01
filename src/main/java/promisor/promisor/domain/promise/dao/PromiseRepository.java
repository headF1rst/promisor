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

import java.util.List;
import java.util.Optional;

public interface PromiseRepository extends JpaRepository<Promise, Long> {

    @Transactional(readOnly = true)
    Slice<Promise> findAllByTeamId(Long id, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query( "update PersonalBanDateReason pbr" +
            " set pbr.reason = :promiseName" +
            " where pbr.personalBanDate =" +
                " (select pb from PersonalBanDate pb " +
                " left join pb.member" +
                " where pb.member = :member" +
                " and function('date_format', pb.date, '%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d'))")
    void updateBandDateOfMembers(@Param("member") Member member,
                                 @Param("date") String date,
                                 @Param("promiseName") String promiseName);
}