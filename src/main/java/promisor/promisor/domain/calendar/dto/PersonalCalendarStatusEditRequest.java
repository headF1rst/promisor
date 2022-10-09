package promisor.promisor.domain.calendar.dto;

import lombok.Data;

@Data
public class PersonalCalendarStatusEditRequest {
    String date;
    String status;
}
