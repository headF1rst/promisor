package promisor.promisor.domain.bandate.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.bandate.domain.PersonalBanDate;
import promisor.promisor.domain.member.domain.Member;

import java.util.List;

@Transactional(readOnly = true)
public interface PersonalBanDateRepository extends JpaRepository<PersonalBanDate, Long> {

    /**
     *
     * 인자로 주어진 멤버와 일시에 해당하는 personalbandate 객체를 가져온다.
     */
    @Transactional
    @Query("select pbd from PersonalBanDate pbd " +
            "join fetch pbd.member where pbd.member = :member and " +
            "function('date_format',pbd.date,'%Y-%m-%d') = function('date_format', :date, '%Y-%m-%d')")
    PersonalBanDate findPersonalBanDateByMemberAndDate(@Param("member") Member member, @Param("date") String date);

    @Transactional(readOnly = true)
    Slice<PersonalBanDate> findAllByMemberId(Long id, Pageable pageable);

    @Query("select pbd from PersonalBanDate pbd " +
            "join fetch pbd.member where pbd.member = :member and pbd.status = 'ACTIVE'")
    List<PersonalBanDate> findAllByMember(@Param("member") Member member);
}
