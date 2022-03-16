package promisor.promisor.domain.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(length = 10)
    @Value("ACTIVE")
    private String status;

    public BaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected BaseEntity() {}
}
