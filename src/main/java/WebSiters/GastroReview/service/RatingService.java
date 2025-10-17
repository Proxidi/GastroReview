package WebSiters.GastroReview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import WebSiters.GastroReview.dto.RatingRequest;
import WebSiters.GastroReview.dto.RatingResponse;

/**
 * Service API for Rating operations.
 * Keeps controllers thin and centralizes business logic and validations.
 */
public interface RatingService {

    Page<RatingResponse> findAll(Pageable pageable);

    Page<RatingResponse> findByReviewId(java.util.UUID reviewId, Pageable pageable);

    Page<RatingResponse> findByUserId(java.util.UUID userId, Pageable pageable);

    RatingResponse findById(Long id);

    RatingResponse create(RatingRequest req);

    RatingResponse update(Long id, RatingRequest req);

    void delete(Long id);
}
