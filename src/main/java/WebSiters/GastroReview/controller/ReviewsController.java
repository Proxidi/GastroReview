package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

/**
 * Controller for reviews. Delegates business logic to ReviewService.
 * The controller remains thin and is responsible for HTTP mapping and error translation.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewService service;

    public ReviewsController(ReviewService service) {
        this.service = service;
    }

    /**
     * List reviews (paged). Optional filters by restaurantId, userId, dishId.
     */
    @GetMapping
    public Page<ReviewResponse> list(Pageable pageable,
                                     @RequestParam(required = false) UUID restaurantId,
                                     @RequestParam(required = false) UUID userId,
                                     @RequestParam(required = false) UUID dishId) {
        if (restaurantId != null) {
            return service.findByRestaurantId(restaurantId, pageable);
        } else if (userId != null) {
            return service.findByUserId(userId, pageable);
        } else if (dishId != null) {
            return service.findByDishId(dishId, pageable);
        } else {
            return service.findAll(pageable);
        }
    }

    /**
     * Get a review by ID.
     */
    @GetMapping("/{id}")
    public ReviewResponse get(@PathVariable UUID id) {
        try {
            return service.findById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Create a new review.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse create(@Valid @RequestBody ReviewRequest in) {
        try {
            return service.create(in);
        } catch (EntityNotFoundException ex) {
            // referenced resources missing (user/restaurant/dish)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    /**
     * Update an existing review.
     */
    @PutMapping("/{id}")
    public ReviewResponse update(@PathVariable UUID id, @Valid @RequestBody ReviewRequest in) {
        try {
            return service.update(id, in);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    /**
     * Delete a review.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
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
