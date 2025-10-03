package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@Builder

public class UserResponse{
        UUID id;
        String email;
        String name;
        boolean active;
        String photoUrl;
        String bio;
        OffsetDateTime createdAt;
}
