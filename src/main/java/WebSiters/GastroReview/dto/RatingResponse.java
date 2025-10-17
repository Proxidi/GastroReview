package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO returned by controllers for Rating.
 */
@Value
@Builder
public class RatingResponse {
    Long id;
    UUID reviewId;
    UUID userId;
    int stars;
    int points;
    OffsetDateTime createdAt;
}
