package promisor.promisor.domain.team.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditTeamRequest {
    @NotBlank(message = "새로운 그룹명을 입력해주세요.")
    private String groupName;
    private Long groupId;
}
