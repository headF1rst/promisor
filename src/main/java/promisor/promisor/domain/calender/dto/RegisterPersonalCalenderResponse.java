package promisor.promisor.domain.calender.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.calender.domain.DateStatusType;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalCalenderResponse {
    private Long id;
    private LocalDate date;
    private DateStatusType dateStatus;

    public RegisterPersonalCalenderResponse(Long id, LocalDate date, DateStatusType dateStatus) {
        this.id = id;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
