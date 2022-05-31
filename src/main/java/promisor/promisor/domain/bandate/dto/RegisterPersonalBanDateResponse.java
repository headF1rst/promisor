package promisor.promisor.domain.bandate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.bandate.domain.DateStatusType;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalBanDateResponse {
    private Long id;
    private LocalDate date;
    private DateStatusType dateStatus;

    public RegisterPersonalBanDateResponse(Long id, LocalDate date, DateStatusType dateStatus) {
        this.id = id;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
