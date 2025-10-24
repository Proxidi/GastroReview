package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Dish entity.
 */
public interface DishRepository extends JpaRepository<Dish, UUID> {
    // find by restaurant id using property path
    Page<Dish> findByRestaurant_Id(UUID restaurantId, Pageable pageable);
    List<Dish> findByRestaurant_Id(UUID restaurantId);
}
