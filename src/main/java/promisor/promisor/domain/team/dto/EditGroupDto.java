package promisor.promisor.domain.team.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditGroupDto {
    @NotBlank(message = "새로운 그룹명을 입력해주세요.")
    private String groupName;
    private Long groupId;
}
