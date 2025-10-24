package WebSiters.GastroReview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import WebSiters.GastroReview.dto.ReviewCommentRequest;
import WebSiters.GastroReview.dto.ReviewCommentResponse;

import java.util.UUID;

/**
 * Service API for ReviewComment operations.
 */
public interface ReviewCommentService {

    Page<ReviewCommentResponse> findAll(Pageable pageable);

    Page<ReviewCommentResponse> findByReviewId(UUID reviewId, Pageable pageable);

    Page<ReviewCommentResponse> findByAuthorId(UUID authorId, Pageable pageable);

    ReviewCommentResponse findById(UUID id);

    ReviewCommentResponse create(ReviewCommentRequest req);

    ReviewCommentResponse update(UUID id, ReviewCommentRequest req);

    void delete(UUID id);
}
