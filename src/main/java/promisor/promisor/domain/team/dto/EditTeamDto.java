package promisor.promisor.domain.team.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditTeamDto {
    @NotBlank(message = "새로운 그룹명을 입력해주세요.")
    private String groupName;
    @NotBlank(message = "수정할 그룹의 id를 입력해주세요.")
    private Long groupId;
}
