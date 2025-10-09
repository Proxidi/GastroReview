package WebSiters.GastroReview.repository;


import WebSiters.GastroReview.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantCategoryRepository<RestaurantCategory> extends JpaRepository<RestaurantCategory, UUID> {

    
    List<RestaurantCategory> findByRestaurantId(UUID restaurantId);
    
    Optional<RestaurantCategory> findByRestaurantIdAndCategoryId(UUID restaurantId, Integer categoryId);
    
    void deleteByRestaurantIdAndCategoryId(UUID restaurantId, Integer categoryId);
    
    boolean existsByRestaurantIdAndCategoryId(UUID restaurantId, Integer categoryId);
}