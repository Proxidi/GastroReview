package WebSiters.GastroReview.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class FriendshipRequest {
    private UUID followerId;
    private UUID followedId;
}
