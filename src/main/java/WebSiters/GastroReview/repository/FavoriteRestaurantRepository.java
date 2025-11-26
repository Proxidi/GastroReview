package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.FavoriteRestaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurant, Object> {

    List<FavoriteRestaurant> findByUserId(UUID userId);
    Page<FavoriteRestaurant> findByUserId(UUID userId, Pageable pageable);

    List<FavoriteRestaurant> findByRestaurantId(UUID restaurantId);
    Page<FavoriteRestaurant> findByRestaurantId(UUID restaurantId, Pageable pageable);

    Optional<FavoriteRestaurant> findByUserIdAndRestaurantId(UUID userId, UUID restaurantId);

    boolean existsByUserIdAndRestaurantId(UUID userId, UUID restaurantId);

    void deleteByUserIdAndRestaurantId(UUID userId, UUID restaurantId);
}