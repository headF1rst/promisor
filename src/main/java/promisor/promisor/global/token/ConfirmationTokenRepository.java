package promisor.promisor.global.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

//    TODO: Query 추가
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update ConfirmationToken c " +
            "set c.confirmedAt = :confirmedAt " +
            "where c.token = :token")
    int updateConfirmedAt(@Param("token") String token, @Param("confirmedAt") LocalDateTime confirmedAt);
}
