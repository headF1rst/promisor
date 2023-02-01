package promisor.promisor.domain.member.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "이메일 또는 비밀번호를 다시 확인해주세요.")
    private String password;

    @Getter
    @Setter
    public static class GetRefreshTokenDto {

        private Long refreshId;
    }
}
