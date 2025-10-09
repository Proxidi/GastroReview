package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record AlertRequest(
    @NotBlank(message = "Type is required")
    @Pattern(regexp = "negative_review|spam|trending|fraud|other",
             message = "Type must be: negative_review, spam, trending, fraud, or other")
    String type,

    UUID restaurantId,

    UUID reviewId,

    String detail
) {
    public AlertRequest {
        if (restaurantId == null && reviewId == null) {
            throw new IllegalArgumentException("Either restaurantId or reviewId must be provided");
        }
    }
}