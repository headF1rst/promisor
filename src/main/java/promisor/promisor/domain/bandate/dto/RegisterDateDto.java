package promisor.promisor.domain.bandate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class RegisterDateDto {
    private LocalDate date;
}
