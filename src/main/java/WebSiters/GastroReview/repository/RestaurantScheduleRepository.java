package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.RestaurantSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for RestaurantSchedule.
 * Query methods use property path restaurant.id (restaurant_Id).
 */
public interface RestaurantScheduleRepository extends JpaRepository<RestaurantSchedule, Long> {
    Page<RestaurantSchedule> findByRestaurant_Id(UUID restaurantId, Pageable pageable);
    List<RestaurantSchedule> findByRestaurant_Id(UUID restaurantId);

    Page<RestaurantSchedule> findByRestaurant_IdAndWeekday(UUID restaurantId, int weekday, Pageable pageable);
}
