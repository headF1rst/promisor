package promisor.promisor.global.token;

import lombok.Getter;
import lombok.Setter;
import promisor.promisor.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ConfirmationToken {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "app_user_id")
    private Member member;

    protected ConfirmationToken() {}

    private ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Member member) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.member = member;
    }

    public static ConfirmationToken of(String token, LocalDateTime createdAt, LocalDateTime expiredAt, Member member) {
        return new ConfirmationToken(token, createdAt, expiredAt, member);
    }
}
