package promisor.promisor.domain.member.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class FollowFriendRequest {

    private String requesterEmail;
    private String receiverEmail;

    protected FollowFriendRequest() {}

    public FollowFriendRequest(String requesterEmail, String receiverEmail) {
        this.requesterEmail = requesterEmail;
        this.receiverEmail = receiverEmail;
    }
}
