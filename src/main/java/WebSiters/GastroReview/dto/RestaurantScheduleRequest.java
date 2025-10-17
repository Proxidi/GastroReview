package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Request DTO used for creating/updating a schedule.
 * Uses LocalTime for open/close times (Jackson JavaTimeModule expected in app).
 */
@Data
public class RestaurantScheduleRequest {

    @NotNull
    private UUID restaurantId;

    @Min(0)
    @Max(6)
    @NotNull
    private Integer weekday; // use Integer so we can validate presence with @NotNull

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    // Optional: default false if omitted
    private Boolean special;
}
