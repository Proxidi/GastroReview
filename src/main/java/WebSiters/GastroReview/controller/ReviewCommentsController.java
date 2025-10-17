package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.ReviewCommentRequest;
import WebSiters.GastroReview.dto.ReviewCommentResponse;
import WebSiters.GastroReview.service.ReviewCommentService;
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
 * Controller for review comments. Delegates business logic to ReviewCommentService.
 */
@RestController
@RequestMapping("/api/review-comments")
public class ReviewCommentsController {

    private final ReviewCommentService service;

    public ReviewCommentsController(ReviewCommentService service) {
        this.service = service;
    }

    /**
     * List comments (paged). Optional filters: reviewId, authorId, parentId.
     */
    @GetMapping
    public Page<ReviewCommentResponse> list(Pageable pageable,
                                            @RequestParam(required = false) UUID reviewId,
                                            @RequestParam(required = false) UUID authorId,
                                            @RequestParam(required = false) UUID parentId) {
        if (reviewId != null) {
            return service.findByReviewId(reviewId, pageable);
        } else if (authorId != null) {
            return service.findByAuthorId(authorId, pageable);
        } else {
            return service.findAll(pageable);
        }
    }

    /**
     * Get comment by id.
     */
    @GetMapping("/{id}")
    public ReviewCommentResponse get(@PathVariable UUID id) {
        try {
            return service.findById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Create comment.
     * - Missing references -> 400 Bad Request
     * - Business violations -> 400 Bad Request
     * - DB constraints -> 400 Bad Request
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewCommentResponse create(@Valid @RequestBody ReviewCommentRequest in) {
        try {
            return service.create(in);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    /**
     * Update comment.
     */
    @PutMapping("/{id}")
    public ReviewCommentResponse update(@PathVariable UUID id, @Valid @RequestBody ReviewCommentRequest in) {
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
     * Delete comment.
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

    /* ---------------- helper ---------------- */

    private String getRootCauseMessage(DataIntegrityViolationException ex) {
        Throwable root = ex;
        while (root.getCause() != null) root = root.getCause();
        return root.getMessage() != null ? root.getMessage() : ex.getMessage();
    }
}
