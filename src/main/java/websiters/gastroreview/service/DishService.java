package WebSiters.GastroReview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import WebSiters.GastroReview.dto.DishRequest;
import WebSiters.GastroReview.dto.DishResponse;

import java.util.UUID;

/**
 * Service API for Dish operations.
 */
public interface DishService {
    Page<DishResponse> findAll(Pageable pageable);

    Page<DishResponse> findByRestaurantId(UUID restaurantId, Pageable pageable);

    DishResponse findById(UUID id);

    DishResponse create(DishRequest req);

    DishResponse update(UUID id, DishRequest req);

    void delete(UUID id);
}
