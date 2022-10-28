package promisor.promisor.domain.calendar.dto;

import lombok.Data;
import promisor.promisor.domain.calendar.domain.DateStatusType;

import java.time.LocalDate;

@Data
public class GetTeamCalendarStatusResponse {

    private final LocalDate date;
    private final String dateStatus;

    public GetTeamCalendarStatusResponse(LocalDate date, DateStatusType dateStatus) {
        this.date = date;
        this.dateStatus = dateStatus.name();
    }
}
