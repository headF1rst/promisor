package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetTeamCalendarResponse {

    private Long id;
    private Long memberId;
    private String name;
    private Long personalBanDateId;
    private Date date;
    private String dateStatus;

    public GetTeamCalendarResponse(Long id, Long memberId, String name, Long personalBanDateId, Date date, String dateStatus) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.personalBanDateId = personalBanDateId;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
