package promisor.promisor.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
 * Person 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@MappedSuperclass
@Getter
public class Person extends BaseEntity {

    @NotEmpty(message = "이름이 안들어 왔습니다.")
    private String name;

    protected Person() {}

    protected Person(String name) {
        this.name = name;
    }
}
