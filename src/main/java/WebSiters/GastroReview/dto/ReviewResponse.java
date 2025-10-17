package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO returned by controllers for Review.
 */
@Value
@Builder
public class ReviewResponse {
    UUID id;
    UUID userId;
    UUID restaurantId;
    UUID dishId;
    String title;
    String content;
    boolean hasAudio;
    boolean hasImage;
    OffsetDateTime publishedAt;
}
