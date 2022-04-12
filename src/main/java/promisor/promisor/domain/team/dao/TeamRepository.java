package promisor.promisor.domain.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.Team;


@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Transactional
    @Modifying
    @Query("update TeamMember tm "+
            "set tm.status = 'DELETED' where tm.member = :member "+
            "and tm.team = :team")
    int leaveGroup(@Param("member") Member member, @Param("team") Team team);
}