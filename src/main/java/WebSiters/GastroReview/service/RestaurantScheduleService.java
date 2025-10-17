package WebSiters.GastroReview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import WebSiters.GastroReview.dto.RestaurantScheduleRequest;
import WebSiters.GastroReview.dto.RestaurantScheduleResponse;

import java.util.UUID;

/**
 * Service API for RestaurantSchedule operations.
 * Keeps controller thin and centralizes business logic and validations.
 */
public interface RestaurantScheduleService {

    /**
     * Return a paged list of schedules (all restaurants).
     */
    Page<RestaurantScheduleResponse> findAll(Pageable pageable);

    /**
     * Return a paged list of schedules for a specific restaurant.
     */
    Page<RestaurantScheduleResponse> findByRestaurantId(UUID restaurantId, Pageable pageable);

    /**
     * Return a paged list of schedules for a restaurant + weekday.
     */
    Page<RestaurantScheduleResponse> findByRestaurantIdAndWeekday(UUID restaurantId, int weekday, Pageable pageable);

    /**
     * Find schedule by its id.
     */
    RestaurantScheduleResponse findById(Long id);

    /**
     * Create a new schedule. Validates times and restaurant existence.
     */
    RestaurantScheduleResponse create(RestaurantScheduleRequest req);

    /**
     * Update an existing schedule. Forbids changing the restaurant via this API.
     */
    RestaurantScheduleResponse update(Long id, RestaurantScheduleRequest req);

    /**
     * Delete a schedule by id.
     */
    void delete(Long id);
}
