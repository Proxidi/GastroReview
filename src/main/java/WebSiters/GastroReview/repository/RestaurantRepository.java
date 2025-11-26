package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    
    List<Restaurant> findByActive(boolean active);
    
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    
    List<Restaurant> findByPriceRange(String priceRange);
    
    List<Restaurant> findByRatingGreaterThanEqual(Double rating);
    
    List<Restaurant> findByActiveOrderByRatingDesc(boolean active);
    
    Optional<Restaurant> findByNameAndAddressId(String name, Long addressId);
    
    boolean existsByNameAndAddressId(String name, Long addressId);
}