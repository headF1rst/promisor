package promisor.promisor.domain.bandate.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetPersonalCalendarResponse {
    private Long id;
    private Long memberId;
    private LocalDate date;
    private String dateStatus;

    public GetPersonalCalendarResponse(Long id, Long memberId, LocalDate date, String dateStatus) {
        this.id = id;
        this.memberId = memberId;
        this.date = date;
        this.dateStatus = dateStatus;
    }
}
