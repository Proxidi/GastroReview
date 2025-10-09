package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotNull(message = "Restaurant ID is required")
    UUID restaurantId,

    UUID dishId,

    String title,

    String content,

    Boolean hasAudio,

    Boolean hasImage
) {}