package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GetPersonalCalendarResponse {
    private Long id;
    private Long memberId;
    private Date date;
    private String dateStatus;

    public GetPersonalCalendarResponse(Long id, Long memberId, Date date, String dateStatus) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
