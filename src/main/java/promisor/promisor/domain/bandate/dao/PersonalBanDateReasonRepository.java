package promisor.promisor.domain.bandate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.bandate.domain.PersonalBanDateReason;
import promisor.promisor.domain.member.domain.Member;

import java.util.List;

@Transactional(readOnly = true)
public interface PersonalBanDateReasonRepository extends JpaRepository<PersonalBanDateReason, Long>{

    @Query(value = "select * from personal_ban_date_reason pbdr join personal_ban_date pbd on pbdr.personal_ban_date_id = pbd.id\n" +
            "join member m on pbd.member_id = m.id\n" +
            "where pbd.member_id = :memberId",
    nativeQuery = true)
    List<PersonalBanDateReason> findAllByMember(@Param("memberId") Long id);

    @Query("select pbdr from PersonalBanDateReason pbdr join pbdr.personalBanDate pbd where pbd = :pbd")
    List<PersonalBanDateReason> findAllByPBD(@Param("pbd") PersonalBanDate pbd);
}
