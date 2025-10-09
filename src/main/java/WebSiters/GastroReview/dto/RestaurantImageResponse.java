package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record RestaurantImageResponse(
    Long id,
    UUID restaurantId,
    String url,
    String altText,
    UUID uploadedBy,
    OffsetDateTime createdAt
) {}