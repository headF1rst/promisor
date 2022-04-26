package promisor.promisor.domain.promise.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.promise.domain.Promise;

import java.util.List;
import java.util.Optional;

public interface PromiseRepository extends JpaRepository<Promise, Long> {

    @Transactional(readOnly = true)
    Slice<Promise> findAllByTeamId(Long id, Pageable pageable);
}
