package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Team;
import promisor.promisor.domain.team.domain.TeamMember;

import java.util.List;

@Transactional(readOnly = true)
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @Transactional
    @Modifying
    @Query("update TeamMember tm "+
            "set tm.status = 'DELETED' where tm.member = :member "+
            "and tm.team = :team")
    void leaveGroup(@Param("member") Member member, @Param("team") Team team);


    List<TeamMember> findTeamMembersByTeam(Team team);

    @Query("select tm from TeamMember tm where tm.member.id = :id and tm.team.id = :teamId")
    TeamMember findMemberByMemberIdAndTeamId(@Param("id") Long id, @Param("teamId") Long teamId);
}
