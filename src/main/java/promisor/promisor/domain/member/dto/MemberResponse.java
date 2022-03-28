package promisor.promisor.domain.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.member.domain.Member;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private Long id;
    private String email;
    private String name;
    private String profileImage;
    private String status;

    public MemberResponse(Member member) {
        this(member.getId(), member.getEmail(), member.getName(), member.getImageUrl(), member.getStatus());
    }

    public MemberResponse(Long id, String email, String name, String profileImage, String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.status = status;
    }
}
