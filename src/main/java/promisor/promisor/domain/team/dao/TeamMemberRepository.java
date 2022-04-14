package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.team.domain.TeamMember;

@Transactional(readOnly = true)
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
