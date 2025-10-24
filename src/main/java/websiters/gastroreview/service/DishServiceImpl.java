package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.DishRequest;
import WebSiters.GastroReview.dto.DishResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Dish;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.repository.DishRepository;
import WebSiters.GastroReview.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of DishService.
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<DishResponse> findAll(Pageable pageable) {
        return dishRepository.findAll(pageable).map(Mappers::toDto);
    }

    @Override
    public Page<DishResponse> findByRestaurantId(UUID restaurantId, Pageable pageable) {
        return dishRepository.findByRestaurant_Id(restaurantId, pageable).map(Mappers::toDto);
    }

    @Override
    public DishResponse findById(UUID id) {
        Dish d = dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found: " + id));
        return Mappers.toDto(d);
    }

    @Override
    @Transactional
    public DishResponse create(DishRequest req) {
        // Resolve restaurant entity (throws EntityNotFoundException -> caller handles)
        Restaurant restaurant = restaurantRepository.findById(req.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + req.getRestaurantId()));

        // Build entity and save
        Dish toSave = Mappers.toEntity(req, restaurant);
        try {
            Dish saved = dishRepository.save(toSave);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            // propagate: controller will convert to appropriate HTTP status
            throw ex;
        }
    }

    @Override
    @Transactional
    public DishResponse update(UUID id, DishRequest req) {
        Dish existing = dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found: " + id));

        // Do not allow changing restaurant via this update endpoint
        if (req.getRestaurantId() != null && !req.getRestaurantId().equals(existing.getRestaurant().getId())) {
            throw new IllegalArgumentException("Changing restaurant is not allowed via this endpoint");
        }

        // Apply mutable fields from request
        Mappers.updateEntityFromRequest(req, existing);

        try {
            Dish saved = dishRepository.save(existing);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!dishRepository.existsById(id)) {
            throw new EntityNotFoundException("Dish not found: " + id);
        }
        dishRepository.deleteById(id);
    }
}
