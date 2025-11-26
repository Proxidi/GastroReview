package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotNull(message = "Restaurant ID is required")
    UUID restaurantId,

    UUID dishId,

    @Size(max = 200, message = "Title must not exceed 200 characters")
    String title,

    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    String content,

    Boolean hasAudio,

    Boolean hasImage
) {}