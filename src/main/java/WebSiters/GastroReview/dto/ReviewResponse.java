package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReviewResponse(
    UUID id,
    UUID userId,
    UUID restaurantId,
    UUID dishId,
    String title,
    String content,
    Boolean hasAudio,
    Boolean hasImage,
    OffsetDateTime publishedAt
) {}