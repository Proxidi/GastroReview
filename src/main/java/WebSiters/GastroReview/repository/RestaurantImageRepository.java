package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
    
    List<RestaurantImage> findByRestaurantId(UUID restaurantId);
    
    List<RestaurantImage> findByUploadedBy(UUID uploadedBy);
    
    boolean existsByIdAndRestaurantId(Long id, UUID restaurantId);
}