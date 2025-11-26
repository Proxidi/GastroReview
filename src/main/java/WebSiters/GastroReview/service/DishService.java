package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.DishRequest;
import WebSiters.GastroReview.dto.DishResponse;
import WebSiters.GastroReview.model.Dish;
import WebSiters.GastroReview.repository.DishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DishService {

    private final DishRepository repo;

    public DishService(DishRepository repo) {
        this.repo = repo;
    }

    public List<DishResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Page<DishResponse> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public List<DishResponse> listarPorRestaurante(UUID restaurantId) {
        return repo.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse).toList();
    }

    public Page<DishResponse> listarPorRestaurante(UUID restaurantId, Pageable pageable) {
        return repo.findByRestaurantId(restaurantId, pageable).map(this::toResponse);
    }

    public List<DishResponse> listarDisponibles() {
        return repo.findByAvailable(true).stream()
                .map(this::toResponse).toList();
    }

    public Page<DishResponse> listarDisponibles(Pageable pageable) {
        return repo.findByAvailable(true, pageable).map(this::toResponse);
    }

    public List<DishResponse> listarDisponiblesPorRestaurante(UUID restaurantId) {
        return repo.findByRestaurantIdAndAvailable(restaurantId, true).stream()
                .map(this::toResponse).toList();
    }

    public Page<DishResponse> listarDisponiblesPorRestaurante(UUID restaurantId, Pageable pageable) {
        return repo.findByRestaurantIdAndAvailable(restaurantId, true, pageable).map(this::toResponse);
    }

    public DishResponse obtener(UUID id) {
        Dish dish = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));
        return toResponse(dish);
    }

    public DishResponse crear(DishRequest req) {
        Dish dish = new Dish();
        dish.setId(UUID.randomUUID());
        dish.setRestaurantId(req.restaurantId());
        dish.setName(req.name());
        dish.setDescription(req.description());
        dish.setPriceCents(req.priceCents());
        dish.setAvailable(req.available() != null ? req.available() : true);

        return toResponse(repo.save(dish));
    }

    public DishResponse actualizar(UUID id, DishRequest req) {
        Dish existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + id));

        existing.setRestaurantId(req.restaurantId());
        existing.setName(req.name());
        existing.setDescription(req.description());
        existing.setPriceCents(req.priceCents());
        existing.setAvailable(req.available() != null ? req.available() : existing.isAvailable());
        
        return toResponse(repo.save(existing));
    }

    public void eliminar(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Dish not found: " + id);
        }
        repo.deleteById(id);
    }

    private DishResponse toResponse(Dish dish) {
        return new DishResponse(
            dish.getId(),
            dish.getRestaurantId(),
            dish.getName(),
            dish.getDescription(),
            dish.getPriceCents(),
            dish.isAvailable(),
            dish.getCreatedAt()
        );
    }
}