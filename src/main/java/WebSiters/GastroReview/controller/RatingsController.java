package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.RatingRequest;
import WebSiters.GastroReview.dto.RatingResponse;
import WebSiters.GastroReview.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;

/**
 * Controller for ratings. Delegates business logic to RatingService.
 * The controller translates service exceptions into appropriate HTTP responses:
 *  - EntityNotFoundException -> 404 (or 400 on create when referenced resource missing)
 *  - IllegalStateException -> 409 Conflict (duplicate rating)
 *  - IllegalArgumentException -> 400 Bad Request (business validation)
 *  - DataIntegrityViolationException -> 400 Bad Request (DB constraint)
 */
@RestController
@RequestMapping("/api/ratings")
public class RatingsController {

    private final RatingService service;

    public RatingsController(RatingService service) {
        this.service = service;
    }

    /**
     * List ratings with optional filters for reviewId or userId.
     */
    @GetMapping
    public Page<RatingResponse> list(Pageable pageable,
                                     @RequestParam(required = false) java.util.UUID reviewId,
                                     @RequestParam(required = false) java.util.UUID userId) {
        if (reviewId != null) {
            return service.findByReviewId(reviewId, pageable);
        } else if (userId != null) {
            return service.findByUserId(userId, pageable);
        } else {
            return service.findAll(pageable);
        }
    }

    /**
     * Get rating by id.
     */
    @GetMapping("/{id}")
    public RatingResponse get(@PathVariable Long id) {
        try {
            return service.findById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Create a new rating.
     * - If referenced review/user not found -> 400 Bad Request
     * - If duplicate rating exists -> 409 Conflict
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse create(@Valid @RequestBody RatingRequest in) {
        try {
            return service.create(in);
        } catch (EntityNotFoundException ex) {
            // referenced resources missing
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (IllegalStateException ex) {
            // duplicate rating
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    /**
     * Update existing rating (only stars/points).
     */
    @PutMapping("/{id}")
    public RatingResponse update(@PathVariable Long id, @Valid @RequestBody RatingRequest in) {
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
     * Delete a rating.
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
