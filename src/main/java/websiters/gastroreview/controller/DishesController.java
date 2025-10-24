package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.DishRequest;
import WebSiters.GastroReview.dto.DishResponse;
import WebSiters.GastroReview.service.DishService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Controller for dishes endpoints. Delegates business logic to DishService.
 */
@RestController
@RequestMapping("/api/dishes")
public class DishesController {

    private final DishService dishService;

    public DishesController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public Page<DishResponse> list(Pageable pageable,
                                  @RequestParam(required = false) UUID restaurantId) {
        if (restaurantId != null) {
            return dishService.findByRestaurantId(restaurantId, pageable);
        } else {
            return dishService.findAll(pageable);
        }
    }

    @GetMapping("/{id}")
    public DishResponse get(@PathVariable UUID id) {
        try {
            return dishService.findById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishResponse create(@Valid @RequestBody DishRequest in) {
        try {
            return dishService.create(in);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    @PutMapping("/{id}")
    public DishResponse update(@PathVariable UUID id, @Valid @RequestBody DishRequest in) {
        try {
            return dishService.update(id, in);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation: " + getRootCauseMessage(ex));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        try {
            dishService.delete(id);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /* helper to extract root cause message from DB exceptions */
    private String getRootCauseMessage(DataIntegrityViolationException ex) {
        Throwable root = ex;
        while (root.getCause() != null) root = root.getCause();
        return root.getMessage() != null ? root.getMessage() : ex.getMessage();
    }
}
