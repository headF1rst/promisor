package promisor.promisor.user;

import lombok.Getter;
import org.springframework.web.bind.annotation.RestController;
import promisor.promisor.model.Person;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * User 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class User extends Person {

    @Lob
    private String imageUrl;

    private int mannerPoint;

    @Column(length = 100)
    private String location;

    @Column
    @NotEmpty
    @Digits(fraction = 0, integer = 11)
    private String telephone;
}
