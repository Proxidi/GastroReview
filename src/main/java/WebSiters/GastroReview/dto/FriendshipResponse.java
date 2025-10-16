package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class FriendshipResponse {
    private UUID followerId;
    private UUID followedId;
    private OffsetDateTime createdAt;
}
