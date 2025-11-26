package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.ReviewAudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewAudioRepository extends JpaRepository<ReviewAudio, Long> {
    
    List<ReviewAudio> findByReviewId(UUID reviewId);
    
    void deleteByReviewId(UUID reviewId);
}