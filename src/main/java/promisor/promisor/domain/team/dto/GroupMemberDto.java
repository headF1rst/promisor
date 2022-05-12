package promisor.promisor.domain.team.dto;

import lombok.Data;
import promisor.promisor.domain.member.domain.Member;
import promisor.promisor.domain.team.domain.TeamMember;

@Data
public class GroupMemberDto {

    private String memberName;

    public GroupMemberDto(Member member) {
        this.memberName = member.getName();
    }

    public GroupMemberDto(TeamMember teamMember) {
        this.memberName = teamMember.getMember().getName();
    }

}
