package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Request DTO for creating/updating review comments.
 * - For create: reviewId, authorId and content are required.
 * - parentId is optional (reply).
 * - publishedAt optional (usually omitted and set by DB/entity).
 */
@Data
public class ReviewCommentRequest {

    @NotNull(message = "reviewId is required")
    private UUID reviewId;

    @NotNull(message = "authorId is required")
    private UUID authorId;

    @NotBlank(message = "content is required")
    private String content;

    // optional override of publishedAt
    private OffsetDateTime publishedAt;
}
