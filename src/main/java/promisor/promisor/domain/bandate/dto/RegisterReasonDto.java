package promisor.promisor.domain.bandate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@NoArgsConstructor
public class RegisterReasonDto {
    @NotBlank(message = "날짜를 입력하세요.")
    private String date;
    @Nullable
    private String reason;
}
