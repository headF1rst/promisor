package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Invite;
import promisor.promisor.domain.team.domain.Team;

@Transactional(readOnly = true)
public interface InviteRepository extends JpaRepository<Invite, Long> {
}
