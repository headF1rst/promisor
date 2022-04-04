package promisor.promisor.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Relation;

import java.util.List;

@Transactional(readOnly = true)
public interface RelationRepository extends JpaRepository<Relation, Long> {

    List<Relation> findByOwnerEmail(String receiverEmail);

    List<Relation> findByFriendEmail(String requesterEmail);

    boolean existsByOwnerEmailAndFriendEmail(String requesterEmail, String receiverEmail);


}