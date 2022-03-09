package promisor.promisor.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Member m " +
            "set m.status = 'ACTIVE' where m.email = ?1")
    int enableMember(String email);

    boolean existsByEmail(String email);
}
