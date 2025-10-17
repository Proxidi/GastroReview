package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantScheduleRequest;
import WebSiters.GastroReview.dto.RestaurantScheduleResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.model.RestaurantSchedule;
import WebSiters.GastroReview.repository.RestaurantRepository;
import WebSiters.GastroReview.repository.RestaurantScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Implementation of RestaurantScheduleService.
 *
 * Responsibilities:
 * - Resolve Restaurant before creating a schedule.
 * - Validate business rules (openTime < closeTime).
 * - Disallow changing restaurant on update.
 * - Wrap repository operations; let controller translate DB exceptions to HTTP.
 */
@Service
@RequiredArgsConstructor
public class RestaurantScheduleServiceImpl implements RestaurantScheduleService {

    private final RestaurantScheduleRepository repo;
    private final RestaurantRepository restaurantRepo;

    @Override
    public Page<RestaurantScheduleResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @Override
    public Page<RestaurantScheduleResponse> findByRestaurantId(UUID restaurantId, Pageable pageable) {
        return repo.findByRestaurant_Id(restaurantId, pageable).map(Mappers::toDto);
    }

    @Override
    public Page<RestaurantScheduleResponse> findByRestaurantIdAndWeekday(UUID restaurantId, int weekday, Pageable pageable) {
        return repo.findByRestaurant_IdAndWeekday(restaurantId, weekday, pageable).map(Mappers::toDto);
    }

    @Override
    public RestaurantScheduleResponse findById(Long id) {
        RestaurantSchedule s = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));
        return Mappers.toDto(s);
    }

    @Override
    @Transactional
    public RestaurantScheduleResponse create(RestaurantScheduleRequest req) {
        // Basic validation of times early so we fail fast
        validateTimes(req.getOpenTime(), req.getCloseTime());

        // Resolve restaurant (throws EntityNotFoundException if missing)
        Restaurant restaurant = restaurantRepo.findById(req.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + req.getRestaurantId()));

        // Build entity using mapper that expects a resolved Restaurant
        RestaurantSchedule toSave = Mappers.toEntity(req, restaurant);

        try {
            RestaurantSchedule saved = repo.save(toSave);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            // Propagate; controller will map to HTTP status and message
            throw ex;
        }
    }

    @Override
    @Transactional
    public RestaurantScheduleResponse update(Long id, RestaurantScheduleRequest req) {
        RestaurantSchedule existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found: " + id));

        // Forbid changing restaurant via this endpoint
        if (req.getRestaurantId() != null && !req.getRestaurantId().equals(existing.getRestaurant().getId())) {
            throw new IllegalArgumentException("Changing restaurant is not allowed via this endpoint");
        }

        // Validate times (use provided or existing)
        LocalTime newOpen = req.getOpenTime() != null ? req.getOpenTime() : existing.getOpenTime();
        LocalTime newClose = req.getCloseTime() != null ? req.getCloseTime() : existing.getCloseTime();
        validateTimes(newOpen, newClose);

        // Apply changes via mapper
        Mappers.updateEntityFromRequest(req, existing);

        try {
            RestaurantSchedule saved = repo.save(existing);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            // Propagate to controller for HTTP mapping
            throw ex;
        } catch (IllegalArgumentException ex) {
            // Re-throw so controller returns a 400-like response
            throw ex;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found: " + id);
        }
        repo.deleteById(id);
    }

    /* ---------------- helpers ---------------- */

    /**
     * Validate that openTime and closeTime are present and open < close.
     * Throws IllegalArgumentException on invalid input.
     */
    private void validateTimes(LocalTime open, LocalTime close) {
        if (open == null || close == null) {
            throw new IllegalArgumentException("openTime and closeTime are required");
        }
        if (!open.isBefore(close)) {
            throw new IllegalArgumentException("openTime must be strictly before closeTime");
        }
    }

    /**
     * Helper to extract root cause of DB exceptions if needed by callers.
     * Not used directly here, kept for parity with controller-level messages.
     */
    private String getRootCauseMessage(DataIntegrityViolationException ex) {
        Throwable root = ex;
        while (root.getCause() != null) root = root.getCause();
        return root.getMessage() != null ? root.getMessage() : ex.getMessage();
    }
}
