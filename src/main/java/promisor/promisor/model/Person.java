package promisor.promisor.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Lob;
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

    @NotEmpty
    private String name;

    @Lob
    @NotEmpty
    private String password;
}
