package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.dto.GetMyTeamResponse;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t.id, t.groupName, t.imageUrl " +
            "from Member m " +
            "left join Team t " +
            "where m.id = :id")
//    @Query("select t.id, t.groupName, t.imageUrl " +
//            "from Team t " +
//            "where t.member.id = :id")
    List<GetMyTeamResponse> findMemberGroups(@Param("id") Long id);

    Optional<Team> findByMember(Member member);
}
