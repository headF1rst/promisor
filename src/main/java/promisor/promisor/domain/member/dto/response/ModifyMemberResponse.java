package promisor.promisor.domain.member.dto.response;

import lombok.Getter;

@Getter
public class ModifyMemberResponse {
    private final String name;
    private final String imageUrl;
    private final String location;

    public ModifyMemberResponse(String name, String imageUrl, String location){
        this.name = name;
        this.imageUrl = imageUrl;
        this.location = location;
    }
}
