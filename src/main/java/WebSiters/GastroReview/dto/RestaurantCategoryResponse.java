package WebSiters.GastroReview.dto;

import java.util.UUID;

public record RestaurantCategoryResponse(
    UUID restaurantId,
    Integer categoryId
) {}