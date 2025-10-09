package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AlertResponse(
    Long id,
    String type,
    UUID restaurantId,
    UUID reviewId,
    String detail,
    OffsetDateTime createdAt
) {}