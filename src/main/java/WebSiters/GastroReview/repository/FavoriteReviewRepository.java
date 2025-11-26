package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.FavoriteReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteReviewRepository extends JpaRepository<FavoriteReview, Object> {
    
    List<FavoriteReview> findByUserId(UUID userId);
    
    List<FavoriteReview> findByReviewId(UUID reviewId);
    
    Optional<FavoriteReview> findByUserIdAndReviewId(UUID userId, UUID reviewId);
    
    boolean existsByUserIdAndReviewId(UUID userId, UUID reviewId);
    
    void deleteByUserIdAndReviewId(UUID userId, UUID reviewId);
}