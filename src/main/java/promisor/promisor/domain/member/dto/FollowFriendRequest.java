package promisor.promisor.domain.member.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class FollowFriendRequest {

    private String receiverEmail;

    protected FollowFriendRequest() {}
}
