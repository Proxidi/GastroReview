package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FavoriteRestaurantResponse(
    UUID userId,
    UUID restaurantId,
    OffsetDateTime createdAt
) {}