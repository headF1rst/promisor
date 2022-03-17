package promisor.promisor.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import promisor.promisor.domain.member.domain.Relation;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    List<Relation> findByOwnerEmail(String receiverEmail);

    List<Relation> findByFriendEmail(String requesterEmail);

    boolean existByOwnerEmailAndFriendEmail(String requesterEmail, String receiverEmail);
}