package promisor.promisor.domain.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import promisor.promisor.domain.member.domain.Member;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private Long id;
    private String profileImage;
    private String name;
    private String telephone;
    private String status;

    public MemberResponse(Member member) {
        this(member.getId(), member.getImageUrl(), member.getName(), member.getTelephone(), member.getStatus());
    }

    public MemberResponse(Long id, String profileImage, String name, String telephone, String status) {
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
        this.telephone = telephone;
        this.status = status;
    }
}
