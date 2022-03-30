package promisor.promisor.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * Person 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@MappedSuperclass
@Getter
public class Person extends BaseEntity {

    @Column(nullable = false)
    private String name;

    protected Person() {}

    protected Person(String name) {
        this.name = name;
    }

    protected Person(String name, String status) {
        super(status);
        this.name = name;
    }
}
