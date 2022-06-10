package promisor.promisor.domain.promise.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PromiseCreateRequest {

    @NotNull(message = "생성하실 약속의 이름을 입력해주세요.")
    private String name;
}
