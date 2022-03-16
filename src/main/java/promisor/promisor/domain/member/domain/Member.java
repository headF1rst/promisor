package promisor.promisor.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import promisor.promisor.domain.model.Person;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * Member 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Person implements UserDetails {

    @Lob
    @Column(nullable = false, unique = true)
    private String email;

    @Lob
    @Column(nullable = false, unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Lob
    private String imageUrl;

    private int mannerPoint;

    @Column(length = 100)
    private String location;

    @Column
    @NotEmpty
    @Digits(fraction = 0, integer = 11)
    private String telephone;

    private Member(String name, String email, String password, String telephone, MemberRole memberRole) {

        super(name, LocalDateTime.now(), LocalDateTime.now());
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.memberRole = memberRole;
    }

    public String getRole() {
        return memberRole.role;
    }

    public static Member of(String name, String email, String password, String telephone, MemberRole memberRole) {
        return new Member(name, email, password,  telephone, memberRole);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(memberRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (getStatus() == "ACTIVE") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (getStatus() == "ACTIVE") {
            return true;
        } else {
            return false;
        }
    }

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
