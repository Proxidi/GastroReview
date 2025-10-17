package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Response DTO returned by controller for schedules.
 */
@Value
@Builder
public class RestaurantScheduleResponse {
    Long id;
    UUID restaurantId;
    int weekday;
    LocalTime openTime;
    LocalTime closeTime;
    boolean special;
}
