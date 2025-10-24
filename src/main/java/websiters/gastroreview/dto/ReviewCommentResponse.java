package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO for review comments.
 */
@Value
@Builder
public class ReviewCommentResponse {
    UUID id;
    UUID reviewId;
    UUID authorId;
    String content;
    OffsetDateTime publishedAt;
}
