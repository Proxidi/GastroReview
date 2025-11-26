package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DishResponse(
    UUID id,
    UUID restaurantId,
    String name,
    String description,
    Integer priceCents,
    Boolean available,
    OffsetDateTime createdAt
) {}