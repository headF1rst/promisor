package promisor.promisor.domain.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 공통 적용 정보 모아둔 엔티티
 *
 * @author Sanha Ko
 */
@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Column(length = 10)
    private String status;

}
