package promisor.promisor.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data

public class ModifyMemberDto {
    private String name;
    private String imageUrl;
    private String location;
}

