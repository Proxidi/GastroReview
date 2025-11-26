package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    
    List<ReviewImage> findByReviewId(UUID reviewId);
    
    void deleteByReviewId(UUID reviewId);
}