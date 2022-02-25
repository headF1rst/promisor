package promisor.promisor.domain.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import promisor.promisor.domain.model.Person;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Collections;

/**
 * User 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
public class User extends Person implements UserDetails {

    @Lob
    @NotEmpty
    private String email;

    @Lob
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Lob
    private String imageUrl;

    private int mannerPoint;

    @Column(length = 100)
    private String location;

    @Column
    @NotEmpty
    @Digits(fraction = 0, integer = 11)
    private String telephone;


    public User(String name, String email, String password,  String telephone, UserRole userRole) {

        super(name);
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
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
