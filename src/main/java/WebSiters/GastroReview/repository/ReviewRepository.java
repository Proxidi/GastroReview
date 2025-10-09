package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    
    List<Review> findByUserId(UUID userId);
    
    List<Review> findByRestaurantId(UUID restaurantId);
    
    List<Review> findByDishId(UUID dishId);
    
    List<Review> findByRestaurantIdOrderByPublishedAtDesc(UUID restaurantId);
    
    List<Review> findByUserIdOrderByPublishedAtDesc(UUID userId);
    
    boolean existsByUserIdAndRestaurantId(UUID userId, UUID restaurantId);
    
    long countByRestaurantId(UUID restaurantId);
    
    long countByUserId(UUID userId);
}