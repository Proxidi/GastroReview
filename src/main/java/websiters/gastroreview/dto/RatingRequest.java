package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

/**
 * DTO used for creating/updating a Rating.
 * - For create: reviewId, userId and stars are required.
 * - For update: only stars and points are considered; changing reviewId/userId is forbidden by controller logic.
 */
@Data
public class RatingRequest {

    @NotNull(message = "reviewId is required")
    private UUID reviewId;

    @NotNull(message = "userId is required")
    private UUID userId;

    @NotNull(message = "stars is required")
    @Min(1)
    @Max(5)
    private Integer stars;

    // Optional override of points (defaults to 0)
    private Integer points;
}
