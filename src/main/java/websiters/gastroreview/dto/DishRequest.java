package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

/**
 * DTO used for creating/updating a Dish.
 * Use @Valid on controller endpoints to enforce constraints.
 */
@Data
public class DishRequest {
    @NotNull
    private UUID restaurantId;

    @NotBlank
    private String name;

    private String description;

    @Min(0)
    private Integer priceCents;

    // Optional: if omitted in a create, default will be true via entity prePersist
    private Boolean available;
}
