package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {

    List<Dish> findByRestaurantId(UUID restaurantId);
    Page<Dish> findByRestaurantId(UUID restaurantId, Pageable pageable);

    List<Dish> findByRestaurantIdAndAvailable(UUID restaurantId, boolean available);
    Page<Dish> findByRestaurantIdAndAvailable(UUID restaurantId, boolean available, Pageable pageable);

    List<Dish> findByAvailable(boolean available);
    Page<Dish> findByAvailable(boolean available, Pageable pageable);
}