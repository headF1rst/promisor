package promisor.promisor.domain.team.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateTeamDto {

    @NotBlank(message = "그룹명을 입력해주세요.")
    @Length(min = 1, max = 30, message = "30자 이내의 그룹이름을 입력해 주세요.")
    private String groupName;
}
