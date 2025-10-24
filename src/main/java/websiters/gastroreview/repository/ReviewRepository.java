package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Review entity.
 */
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findByRestaurant_Id(UUID restaurantId, Pageable pageable);
    List<Review> findByRestaurant_Id(UUID restaurantId);

    Page<Review> findByUser_Id(UUID userId, Pageable pageable);
    List<Review> findByUser_Id(UUID userId);

    Page<Review> findByDish_Id(UUID dishId, Pageable pageable);
    List<Review> findByDish_Id(UUID dishId);
}
