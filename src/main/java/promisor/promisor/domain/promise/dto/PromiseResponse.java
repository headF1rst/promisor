package promisor.promisor.domain.promise.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PromiseResponse {

    private Long id;
    private String name;
    private LocalDateTime time;
    private String location;

    public PromiseResponse(Long id, String name, LocalDateTime time, String location) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.location = location;
    }
}
