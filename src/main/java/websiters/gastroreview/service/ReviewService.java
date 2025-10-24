package WebSiters.GastroReview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;

import java.util.UUID;

/**
 * Service API for Review operations.
 * Keeps controllers thin and centralizes business logic and validations.
 */
public interface ReviewService {

    Page<ReviewResponse> findAll(Pageable pageable);

    Page<ReviewResponse> findByRestaurantId(UUID restaurantId, Pageable pageable);

    Page<ReviewResponse> findByUserId(UUID userId, Pageable pageable);

    Page<ReviewResponse> findByDishId(UUID dishId, Pageable pageable);

    ReviewResponse findById(UUID id);

    ReviewResponse create(ReviewRequest req);

    ReviewResponse update(UUID id, ReviewRequest req);

    void delete(UUID id);
}
