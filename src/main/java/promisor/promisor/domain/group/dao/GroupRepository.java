package promisor.promisor.domain.group.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.group.domain.Group;


@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, Long> { }
