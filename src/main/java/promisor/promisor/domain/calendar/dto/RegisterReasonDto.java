package promisor.promisor.domain.calendar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RegisterReasonDto {
    @NotBlank(message = "날짜를 입력하세요.")
    private String date;
    @Nullable
    private String reason;
}
