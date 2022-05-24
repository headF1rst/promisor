package promisor.promisor.domain.bandate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.domain.TeamBanDate;
import promisor.promisor.domain.member.domain.Member;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface TeamBanDateRepository extends JpaRepository<TeamBanDate, Long> {

    @Query("select tbd from TeamBanDate tbd where tbd.personalBanDate.id=:id")
    Optional<List<TeamBanDate>> getByBanDateId(@Param("id") Long id);
}
