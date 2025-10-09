package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record RestaurantCategoryRequest(
    @NotNull(message = "Restaurant ID is required")
    UUID restaurantId,

    @NotNull(message = "Category ID is required")
    Integer categoryId
) {}