package promisor.promisor.domain.bandate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalBanDateResponse {
    private Long id;
    private LocalDate date;

    public RegisterPersonalBanDateResponse(Long id, LocalDate date){
        this.id = id;
        this.date = date;
    }
}
