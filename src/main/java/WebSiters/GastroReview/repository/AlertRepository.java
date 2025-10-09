package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    
    List<Alert> findByRestaurantId(UUID restaurantId);
    
    List<Alert> findByReviewId(UUID reviewId);
    
    List<Alert> findByType(String type);
    
    List<Alert> findByRestaurantIdAndType(UUID restaurantId, String type);
    
    List<Alert> findByReviewIdAndType(UUID reviewId, String type);
}