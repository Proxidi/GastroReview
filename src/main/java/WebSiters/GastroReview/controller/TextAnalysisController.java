package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.TextAnalysisRequest;
import WebSiters.GastroReview.dto.TextAnalysisResponse;
import WebSiters.GastroReview.service.TextAnalysisService;
import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for text analysis resources.
 * Delegates business logic to TextAnalysisService.
 */
@RestController
@RequestMapping("/api/text-analysis")
public class TextAnalysisController {

    private final TextAnalysisService service;

    public TextAnalysisController(TextAnalysisService service) {
        this.service = service;
    }

    /**
     * List text analysis records (paged). Optional filter by reviewId.
     */
    @GetMapping
    public Page<TextAnalysisResponse> list(Pageable pageable,
                                           @RequestParam(required = false) java.util.UUID reviewId) {
        if (reviewId != null) {
            return service.findByReviewId(reviewId, pageable);
        } else {
            return service.findAll(pageable);
        }
    }

    /**
     * Get a text analysis by id.
     */
    @GetMapping("/{id}")
    public TextAnalysisResponse get(@PathVariable Long id) {
        try {
            return service.findById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Create a text analysis result.
     * - Missing review -> 400 Bad Request
     * - Invalid sentiment or numeric fields -> 400 Bad Request
     * - DB constraint issues -> 400 Bad Request
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TextAnalysisResponse create(@Valid @RequestBody TextAnalysisRequest in) {
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
     * Update a text analysis record.
     */
    @PutMapping("/{id}")
    public TextAnalysisResponse update(@PathVariable Long id, @Valid @RequestBody TextAnalysisRequest in) {
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
     * Delete a text analysis record.
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
