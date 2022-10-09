package promisor.promisor.domain.calendar.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.calendar.domain.DateStatusType;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPersonalCalendarResponse {
    private Long id;
    private LocalDate date;
    private DateStatusType dateStatus;

    public RegisterPersonalCalendarResponse(Long id, LocalDate date, DateStatusType dateStatus) {
        this.id = id;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
