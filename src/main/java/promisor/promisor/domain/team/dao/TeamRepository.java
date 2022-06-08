package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select tm from TeamMember tm join fetch tm.member m "+
            "where m in " +
            "(select t from Team t inner join t.member m on m = :member) " +
            "or m =: member " +
            "order by tm.team.createdAt desc")
    List<TeamMember> findGroupInfoWithMembers(@Param("member") Member member);

    List<Team> findAllByMember(Member member);
}
