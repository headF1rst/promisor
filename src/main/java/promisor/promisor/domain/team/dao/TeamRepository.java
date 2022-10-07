package promisor.promisor.domain.team.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;

import java.util.List;


@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select tm from TeamMember tm join fetch tm.member m "+
            "where m in " +
            "(select t from Team t inner join tm.member m on m = :member) " +
            "or m =: member")
    Slice<TeamMember> findTeamInfoWithMembers(@Param("member") Member member, Pageable pageable);

    @Query("select t from TeamMember tm join fetch tm.team t " +
            "where tm.member in " +
            "(select m from Member m inner join tm.member m on m = :member)")
    List<Team> findAllTeams(@Param("member") Member member);
}
