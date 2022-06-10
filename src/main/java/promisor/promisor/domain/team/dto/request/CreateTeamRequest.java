package promisor.promisor.domain.team.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTeamRequest {

    @NotBlank(message = "그룹명을 입력해주세요.")
    @Length(min = 1, max = 30, message = "30자 이내의 그룹이름을 입력해 주세요.")
    private String groupName;
}
