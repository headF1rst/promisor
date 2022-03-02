package promisor.promisor.global.security.token;

import lombok.Getter;
import lombok.Setter;
import promisor.promisor.domain.member.Member;

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
    private Member user;

    protected ConfirmationToken() {}

    private ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Member user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public static ConfirmationToken of(String token, LocalDateTime createdAt, LocalDateTime expiredAt, Member user) {
        return new ConfirmationToken(token, createdAt, expiredAt, user);
    }
}
