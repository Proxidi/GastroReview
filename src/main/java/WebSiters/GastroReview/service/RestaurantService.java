package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantRequest;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository repo;

    public RestaurantService(RestaurantRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<RestaurantResponse> listar() {
        return repo.findAll().stream()
                .map(Mappers::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RestaurantResponse obtener(UUID id) {
        Restaurant restaurant = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + id));
        return Mappers.toResponse(restaurant);
    }

    public RestaurantResponse crear(RestaurantRequest req) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName(req.name);
        restaurant.setDescription(req.description);
        restaurant.setPhone(req.phone);
        restaurant.setEmail(req.email);
        restaurant.setOwnerId(req.ownerId);
        restaurant.setActive(true);

        Restaurant saved = repo.save(restaurant);
        return Mappers.toResponse(saved);
    }

    public RestaurantResponse actualizar(UUID id, RestaurantRequest req) {
        Restaurant existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + id));

        existing.setName(req.name);
        existing.setDescription(req.description);
        existing.setPhone(req.phone);
        existing.setEmail(req.email);
        existing.setOwnerId(req.ownerId);

        Restaurant updated = repo.save(existing);
        return Mappers.toResponse(updated);
    }

    public void eliminar(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Restaurant not found: " + id);
        }
        repo.deleteById(id);
    }
}
