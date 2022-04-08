package promisor.promisor.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import promisor.promisor.domain.model.Person;
import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.ApplicationException;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Member 도메인 객체를 나타내는 자바 빈
 *
 * @author Sanha Ko
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //무분별한 객체 생성에 대해 한 번 더 체크
public class Member extends Person implements UserDetails {

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Relation> friends = new HashSet<>();

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

    @Column(nullable = false)
    @Digits(fraction = 0, integer = 11)
    private String telephone;

    private Member(String name, String email, String password, String telephone, MemberRole memberRole) {
        super(name, "INACTIVE");
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
        return Objects.equals(getStatus(), "ACTIVE");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(getStatus(), "ACTIVE");
    }

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public List<Member> getMemberFriends() {
        return this.friends.stream()
                .map(Relation::getFriend)
                .sorted(Comparator.comparing(Member::getName))
                .collect(Collectors.toList());
    }

    public boolean hasFriend(Member receiver) {
        List<Member> friends = getMemberFriends();
        System.out.println("friends = " + friends);
        if (friends.isEmpty()) {
            return false;
        }
        return friends.contains(receiver);
    }

    public void modifyMemberInfo(String name, String imageUrl, String location){
        this.name = name;
        this.imageUrl = imageUrl;
        this.location = location;
    public void deleteFriend(Member friend) {

        if (getMemberFriends().contains(friend) && friend.getMemberFriends().contains(this)) {
            friends.removeIf(friendShip -> friendShip.getFriend().equals(friend));
            friend.friends.removeIf(friendShip -> friendShip.getFriend().equals(this));
            return;
        }
        throw new ApplicationException(ErrorCode.FORBIDDEN_USER);
    }
}
