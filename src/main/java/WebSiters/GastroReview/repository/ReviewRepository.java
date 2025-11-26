package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByUserIdOrderByPublishedAtDesc(UUID userId);
    Page<Review> findByUserId(UUID userId, Pageable pageable);

    List<Review> findByRestaurantIdOrderByPublishedAtDesc(UUID restaurantId);
    Page<Review> findByRestaurantId(UUID restaurantId, Pageable pageable);

    List<Review> findByDishIdOrderByPublishedAtDesc(UUID dishId);
    Page<Review> findByDishId(UUID dishId, Pageable pageable);

    List<Review> findByHasAudioTrue();
    Page<Review> findByHasAudioTrue(Pageable pageable);

    List<Review> findByHasImageTrue();
    Page<Review> findByHasImageTrue(Pageable pageable);
}