package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Dish;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.model.Review;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.DishRepository;
import WebSiters.GastroReview.repository.ReviewRepository;
import WebSiters.GastroReview.repository.RestaurantRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of ReviewService.
 *
 * Responsibilities:
 * - Resolve referenced entities (User, Restaurant, optional Dish).
 * - Validate consistency (e.g., Dish belongs to Restaurant).
 * - Orchestrate create/update/delete in transactions.
 * - Throw EntityNotFoundException or IllegalArgumentException for business errors.
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepo;
    private final UsersRepository usersRepo;
    private final RestaurantRepository restaurantRepo;
    private final DishRepository dishRepo;

    @Override
    public Page<ReviewResponse> findAll(Pageable pageable) {
        return reviewRepo.findAll(pageable).map(Mappers::toDto);
    }

    @Override
    public Page<ReviewResponse> findByRestaurantId(UUID restaurantId, Pageable pageable) {
        return reviewRepo.findByRestaurant_Id(restaurantId, pageable).map(Mappers::toDto);
    }

    @Override
    public Page<ReviewResponse> findByUserId(UUID userId, Pageable pageable) {
        return reviewRepo.findByUser_Id(userId, pageable).map(Mappers::toDto);
    }

    @Override
    public Page<ReviewResponse> findByDishId(UUID dishId, Pageable pageable) {
        return reviewRepo.findByDish_Id(dishId, pageable).map(Mappers::toDto);
    }

    @Override
    public ReviewResponse findById(UUID id) {
        Review r = reviewRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + id));
        return Mappers.toDto(r);
    }

    @Override
    @Transactional
    public ReviewResponse create(ReviewRequest req) {
        // Resolve user
        Users user = usersRepo.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getUserId()));

        // Resolve restaurant
        Restaurant restaurant = restaurantRepo.findById(req.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + req.getRestaurantId()));

        // Resolve optional dish
        Dish dish = null;
        if (req.getDishId() != null) {
            dish = dishRepo.findById(req.getDishId())
                    .orElseThrow(() -> new EntityNotFoundException("Dish not found: " + req.getDishId()));

            // Ensure dish belongs to restaurant (consistency)
            if (dish.getRestaurant() != null && !dish.getRestaurant().getId().equals(restaurant.getId())) {
                throw new IllegalArgumentException("Dish does not belong to the provided restaurant");
            }
        }

        Review toSave = Mappers.toEntity(req, user, restaurant, dish);
        try {
            Review saved = reviewRepo.save(toSave);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            // propagate; controller will convert to HTTP
            throw ex;
        }
    }

    @Override
    @Transactional
    public ReviewResponse update(UUID id, ReviewRequest req) {
        Review existing = reviewRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + id));

        // Forbid changing author (user)
        if (req.getUserId() != null && !req.getUserId().equals(existing.getUser().getId())) {
            throw new IllegalArgumentException("Changing user (author) is not allowed");
        }

        // Forbid changing restaurant
        if (req.getRestaurantId() != null && !req.getRestaurantId().equals(existing.getRestaurant().getId())) {
            throw new IllegalArgumentException("Changing restaurant is not allowed via this endpoint");
        }

        // If dishId provided and different, resolve and set (allow dish change)
        if (req.getDishId() != null) {
            if (existing.getDish() == null || !req.getDishId().equals(existing.getDish().getId())) {
                Dish dish = dishRepo.findById(req.getDishId())
                        .orElseThrow(() -> new EntityNotFoundException("Dish not found: " + req.getDishId()));
                // Ensure dish belongs to the review's restaurant
                if (dish.getRestaurant() != null && !dish.getRestaurant().getId().equals(existing.getRestaurant().getId())) {
                    throw new IllegalArgumentException("Dish does not belong to the review's restaurant");
                }
                existing.setDish(dish);
            }
        }

        // Apply other updates using mapper helper
        Mappers.updateEntityFromRequest(req, existing);

        try {
            Review saved = reviewRepo.save(existing);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!reviewRepo.existsById(id)) {
            throw new EntityNotFoundException("Review not found: " + id);
        }
        reviewRepo.deleteById(id);
    }
}
