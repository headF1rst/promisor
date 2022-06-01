package promisor.promisor.domain.bandate.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.domain.TeamBanDate;
import promisor.promisor.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TeamBanDateRepository extends JpaRepository<TeamBanDate, Long> {

    @Transactional(readOnly = true)
    @Query("select tbd from TeamBanDate tbd where tbd.personalBanDate.id=:id")
    Optional<List<TeamBanDate>> getByBanDateId(@Param("id") Long id);

    @Transactional(readOnly = true)
    Slice<TeamBanDate> findAllByTeamId(Long id, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("select tbd from TeamBanDate tbd where tbd.team.id=:id and tbd.date=:date")
    Slice<TeamBanDate> findAllByTeamIdAndDate(Long id, LocalDate date, Pageable pageable);
}
