package promisor.promisor.domain.member.dto;

import lombok.Data;
import promisor.promisor.domain.member.domain.MemberRole;
import promisor.promisor.global.error.exception.Enum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Pattern(regexp = "\\d{11}", message = "\"-\"없이 전화번호 11자리를 입력해주세요.")
    private String telephone;

    @Enum(enumClass = MemberRole.class, ignoreCase = true)
    private String memberRole;
}
