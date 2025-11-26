package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FavoriteReviewResponse(
    UUID userId,
    UUID reviewId,
    OffsetDateTime createdAt
) {}