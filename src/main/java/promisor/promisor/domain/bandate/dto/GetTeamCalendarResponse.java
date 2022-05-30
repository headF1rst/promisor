package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class GetTeamCalendarResponse {

    private Long id;
    private Long memberId;
    private String name;
    private Long personalBanDateId;
    private LocalDate date;
    private String dateStatus;

    public GetTeamCalendarResponse(Long id, Long memberId, String name, Long personalBanDateId, LocalDate date, String dateStatus) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.personalBanDateId = personalBanDateId;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
