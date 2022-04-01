package promisor.promisor.domain.team.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateGroupDto {

    @NotBlank(message = "그룹명을 입력해주세요.")
    private String groupName;
}
