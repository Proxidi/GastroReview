package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Rating entity.
 * Use property path review.id and user.id to filter by related entity ids.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Page<Rating> findByReview_Id(UUID reviewId, Pageable pageable);
    Page<Rating> findByUser_Id(UUID userId, Pageable pageable);

    // Check if a rating by the same user for the same review already exists
    Optional<Rating> findByReview_IdAndUser_Id(UUID reviewId, UUID userId);
}
