package promisor.promisor.domain.calendar.dto;

import lombok.Data;
import promisor.promisor.domain.calendar.domain.DateStatusType;

import java.time.LocalDate;

@Data
public class GetTeamCalendarResponse {

    private Long id;
    private Long memberId;
    private String name;
    private Long personalBanDateId;
    private LocalDate date;
    private String dateStatus;

    public GetTeamCalendarResponse(Long id, Long memberId, String name, Long personalBanDateId, LocalDate date, DateStatusType dateStatus) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.personalBanDateId = personalBanDateId;
        this.date = date;
        this.dateStatus = String.valueOf(dateStatus);
    }
}
