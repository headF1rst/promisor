package promisor.promisor.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Member m " +
            "set m.status = 'ACTIVE' where m.email = :email")
    int enableMember(@Param("email") String email);

    boolean existsByEmail(String email);
}

