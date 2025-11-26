package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByType(String type);
    Page<Alert> findByType(String type, Pageable pageable);

    List<Alert> findByRestaurantId(UUID restaurantId);
    Page<Alert> findByRestaurantId(UUID restaurantId, Pageable pageable);

    List<Alert> findByReviewId(UUID reviewId);
    Page<Alert> findByReviewId(UUID reviewId, Pageable pageable);

    List<Alert> findByRestaurantIdOrderByCreatedAtDesc(UUID restaurantId);

    List<Alert> findByTypeOrderByCreatedAtDesc(String type);
}