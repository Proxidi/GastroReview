package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.RestaurantScheduleRequest;
import WebSiters.GastroReview.dto.RestaurantScheduleResponse;
import WebSiters.GastroReview.service.RestaurantScheduleService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controller for restaurant schedules.
 * <p>
 * This refactored controller is intentionally thin: it delegates all business logic
 * to RestaurantScheduleService and translates service exceptions into HTTP responses.
 * Keep controllers minimal — the service is responsible for validation and transactions.
 */
@RestController
@RequestMapping("/api/restaurant-schedules")
public class RestaurantSchedulesController {

    private final RestaurantScheduleService service;

    public RestaurantSchedulesController(RestaurantScheduleService service) {
        this.service = service;
    }

    /**
     * List schedules (paged). Optional filters: restaurantId, weekday.
     * Delegates filtering logic to the service.
     */
    @GetMapping
    public Page<RestaurantScheduleResponse> list(Pageable pageable,
                                                @RequestParam(required = false) java.util.UUID restaurantId,
                                                @RequestParam(required = false) Integer weekday) {
        if (restaurantId != null && weekday != null) {
            return service.findByRestaurantIdAndWeekday(restaurantId, weekday, pageable);
        } else if (restaurantId != null) {
            return service.findByRestaurantId(restaurantId, pageable);
        } else {
            return service.findAll(pageable);
        }
    }

    /**
     * Convenience path listing by restaurant.
     */
    @GetMapping("/restaurant/{restaurantId}")
    public Page<RestaurantScheduleResponse> listByRestaurant(@PathVariable java.util.UUID restaurantId, Pageable pageable) {
        return service.findByRestaurantId(restaurantId, pageable);
    }

    /**
     * Get schedule by id.
     * Returns 404 if not found.
     */
    @GetMapping("/{id}")
    public RestaurantScheduleResponse get(@PathVariable Long id) {
        try {
            return service.findById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Create schedule.
     * - If a referenced Restaurant is missing, service throws EntityNotFoundException.
     *   Here we map that to 400 Bad Request (invalid reference).
     * - Constraint/database errors are mapped to 400 with DB message.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantScheduleResponse create(@Valid @RequestBody RestaurantScheduleRequest in) {
        try {
            return service.create(in);
        } catch (EntityNotFoundException ex) {
            // Missing referenced resource (e.g., restaurant) — treat as bad input on create
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    /**
     * Update schedule.
     * - If the schedule does not exist -> 404
     * - If business validation fails -> 400
     * - DB constraint issues -> 400
     */
    @PutMapping("/{id}")
    public RestaurantScheduleResponse update(@PathVariable Long id, @Valid @RequestBody RestaurantScheduleRequest in) {
        try {
            return service.update(id, in);
        } catch (EntityNotFoundException ex) {
            // If the schedule itself is not found, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    /**
     * Delete schedule.
     * Returns 404 if schedule not found.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /* ---------------- helpers ---------------- */

    private String getRootCauseMessage(DataIntegrityViolationException ex) {
        Throwable root = ex;
        while (root.getCause() != null) root = root.getCause();
        return root.getMessage() != null ? root.getMessage() : ex.getMessage();
    }
}
