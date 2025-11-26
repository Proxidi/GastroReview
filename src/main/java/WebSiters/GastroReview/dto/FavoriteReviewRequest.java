package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record FavoriteReviewRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotNull(message = "Review ID is required")
    UUID reviewId
) {}