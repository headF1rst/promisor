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
@RequiredArgsConstructor
public class Person extends BaseEntity {

    @NotEmpty
    private final String name;
}
