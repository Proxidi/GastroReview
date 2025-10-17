package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO returned by controllers for Dish.
 * Immutable value object with builder for convenience.
 */
@Value
@Builder
public class DishResponse {
    UUID id;
    UUID restaurantId;
    String name;
    String description;
    Integer priceCents;
    boolean available;
    OffsetDateTime createdAt;
}
