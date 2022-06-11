package promisor.promisor.domain.promise.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PromiseResponse {

    private Long id;
    private String name;
    private LocalDate time;
    private String location;

    public PromiseResponse(Long id, String name, LocalDate time, String location) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.location = location;
    }
}
