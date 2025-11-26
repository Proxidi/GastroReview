package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record DishRequest(
    @NotNull(message = "ID is required")
    UUID id,

    @NotNull(message = "Restaurant ID is required")
    UUID restaurantId,

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    String name,

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    String description,

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    Integer priceCents,

    Boolean available
) {}
