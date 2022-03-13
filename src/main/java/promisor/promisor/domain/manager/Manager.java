package promisor.promisor.domain.manager;

import lombok.Getter;
import promisor.promisor.domain.model.Person;

import javax.persistence.*;

@Entity
@Getter
public class Manager extends Person {

    @Lob
    @Column(nullable = false, unique = true)
    private String email;

    @Lob
    @Column(nullable = false, unique = true)
    private String password;

    @Lob
    private String imageUrl;
}
