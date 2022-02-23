package promisor.promisor.domain.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserDAO {

    Optional<User> findByEmail(String email);
}
