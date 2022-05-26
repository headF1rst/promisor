package promisor.promisor.domain.bandate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.member.domain.Member;

import java.util.Date;

@Transactional(readOnly = true)
public interface PersonalBanDateRepository extends JpaRepository<PersonalBanDate, Long> {
    @Transactional
    @Query("select pbd from PersonalBanDate pbd join fetch pbd.member where pbd.member = :member and " +
            "function('date_format',pbd.date,'%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d')")
    PersonalBanDate getPersonalBanDateByMemberAndDate(@Param("member") Member member, String date);
}
