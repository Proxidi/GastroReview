package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record RestaurantImageRequest(
    @NotNull(message = "Restaurant ID is required")
    UUID restaurantId,

    @NotBlank(message = "URL is required")
    String url,

    String altText,

    UUID uploadedBy
) {}