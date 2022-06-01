package promisor.promisor.domain.member.dto;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ModifyMemberDto {
    @NotBlank(message = "이름을 입력하세요.")
    private String name;
    @Nullable
    private String imageUrl;
    @Nullable
    private String location;
}

