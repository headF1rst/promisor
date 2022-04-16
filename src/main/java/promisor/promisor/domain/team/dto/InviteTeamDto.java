package promisor.promisor.domain.team.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InviteTeamDto {
    private Long memberId;
    private Long groupId;
}
