package promisor.promisor.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.model.Person;
import promisor.promisor.global.error.ErrorCode;
import promisor.promisor.global.error.exception.ApplicationException;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
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

    private Member(String name, String email, String password, String telephone, String imageUrl, MemberRole role) {
        super(name, "INACTIVE");
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.imageUrl = imageUrl;
        this.memberRole = role;
    }

    public static Member of(String name, String email, String password, String telephone, MemberRole memberRole) {
        return new Member(name, email, password, telephone, memberRole);
    }

    public static Member of(String name, String email, String password, String telephone, String imageUrl) {
        return new Member(name, email, password, telephone, imageUrl, MemberRole.USER);
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
        if (getMemberFriends().isEmpty()) {
            return false;
        }
        return getMemberFriends().contains(receiver);
    }

    public void addFriend(Member friend) {
        this.friends.add(new Relation(this, friend, "ACTIVE"));
    }

    public void modifyMemberInfo(String name, String imageUrl, String location) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public void deleteFriend(Member friend) {
        if (getMemberFriends().contains(friend)) {
            friends.removeIf(friendShip -> friendShip.getFriend().equals(friend));
            return;
        }
        throw new ApplicationException(ErrorCode.FORBIDDEN_USER);
    }

    public boolean isNotLeader(Long id) {
        return this.id.equals(id);
    }

    public boolean isLeader(Long id) {
        return this.id.equals(id);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
