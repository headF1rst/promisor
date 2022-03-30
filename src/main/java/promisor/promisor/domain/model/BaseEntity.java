package promisor.promisor.domain.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 공통 적용 정보 모아둔 엔티티
 *
 * @author Sanha Ko
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(length = 10)
    @Value(value = "ACTIVE")
    private String status;

    protected BaseEntity() {}

    protected BaseEntity(String status) {
        this.status = status;
    }
}
