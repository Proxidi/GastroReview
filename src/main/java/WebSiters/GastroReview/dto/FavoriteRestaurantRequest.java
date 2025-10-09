package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record FavoriteRestaurantRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotNull(message = "Restaurant ID is required")
    UUID restaurantId
) {}