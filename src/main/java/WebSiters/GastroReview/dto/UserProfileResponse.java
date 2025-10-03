package WebSiters.GastroReview.dto;


import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@Builder
public class UserProfileResponse {
    UUID userId;
    String photoUrl;
    String name;
    boolean active;
    OffsetDateTime createdAt;
    String bio;
}