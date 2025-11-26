package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantCategoryRequest(
    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Icon is required")
    String icon
) {}