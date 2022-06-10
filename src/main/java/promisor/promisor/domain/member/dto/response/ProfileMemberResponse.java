package promisor.promisor.domain.member.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import promisor.promisor.domain.member.domain.Member;

@Getter
public class ProfileMemberResponse {
    private final Long id;
    private final String name;
    private final String imageUrl;

    public ProfileMemberResponse(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.imageUrl = member.getImageUrl();
    }
}
