package promisor.promisor.domain.member.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowFriendResponse {

    private Long id;
    private MemberResponse requester;
    private MemberResponse receiver;

    public FollowFriendResponse(Long id, MemberResponse requester, MemberResponse receiver) {
        this.id = id;
        this.requester = requester;
        this.receiver = receiver;
    }
}
