package promisor.promisor.domain.BanDate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@NoArgsConstructor
public class RegisterDateDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String reason;
}
