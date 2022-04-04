package promisor.promisor.global.auth;

import lombok.Data;
import promisor.promisor.domain.member.domain.Member;

@Data
public class ProfileTokenResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final String profileImage;
    private final String token;
    private final boolean adminInfo;

    public ProfileTokenResponse(Member member, String token, boolean isAdmin) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.profileImage = member.getImageUrl();
        this.token = token;
        this.adminInfo = isAdmin;
    }
}
