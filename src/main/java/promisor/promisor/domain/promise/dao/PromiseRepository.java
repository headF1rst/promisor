package promisor.promisor.domain.promise.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import promisor.promisor.domain.promise.domain.Promise;

public interface PromiseRepository extends JpaRepository<Promise, Long> {
}
