package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO used for creating/updating reviews.
 * Use @Valid in controller to enforce required fields on create.
 *
 * For updates, fields may be omitted (null) to indicate "no change".
 */
@Data
public class ReviewRequest {
    // Required for creation
    @NotNull(message = "userId is required")
    private UUID userId;

    @NotNull(message = "restaurantId is required")
    private UUID restaurantId;

    // optional
    private UUID dishId;
    private String title;
    private String content;
    private Boolean hasAudio;
    private Boolean hasImage;

    // Optional override of publishedAt; normally omitted and set server-side
    private OffsetDateTime publishedAt;
}

