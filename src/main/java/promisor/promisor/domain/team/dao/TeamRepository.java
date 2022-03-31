package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.team.domain.Team;


@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Long> {

}
