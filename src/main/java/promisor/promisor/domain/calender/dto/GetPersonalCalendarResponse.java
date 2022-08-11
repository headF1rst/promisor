package promisor.promisor.domain.calender.dto;

import lombok.Data;
import promisor.promisor.domain.calender.domain.DateStatusType;

import java.time.LocalDate;

@Data
public class GetPersonalCalendarResponse {
    private Long id;
    private Long memberId;
    private LocalDate date;
    private DateStatusType dateStatus;

    public GetPersonalCalendarResponse(Long id, Long memberId, LocalDate date, DateStatusType dateStatus) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
