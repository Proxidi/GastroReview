package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.FavoriteRestaurantRequest;
import WebSiters.GastroReview.dto.FavoriteRestaurantResponse;
import WebSiters.GastroReview.model.FavoriteRestaurant;
import WebSiters.GastroReview.repository.FavoriteRestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteRestaurantService {

    private final FavoriteRestaurantRepository repo;

    public FavoriteRestaurantService(FavoriteRestaurantRepository repo) {
        this.repo = repo;
    }

    public List<FavoriteRestaurantResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Page<FavoriteRestaurantResponse> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public List<FavoriteRestaurantResponse> listarPorUsuario(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(this::toResponse).toList();
    }

    public Page<FavoriteRestaurantResponse> listarPorUsuario(UUID userId, Pageable pageable) {
        return repo.findByUserId(userId, pageable).map(this::toResponse);
    }

    public List<FavoriteRestaurantResponse> listarPorRestaurante(UUID restaurantId) {
        return repo.findByRestaurantId(restaurantId).stream()
                .map(this::toResponse).toList();
    }

    public Page<FavoriteRestaurantResponse> listarPorRestaurante(UUID restaurantId, Pageable pageable) {
        return repo.findByRestaurantId(restaurantId, pageable).map(this::toResponse);
    }

    public FavoriteRestaurantResponse obtener(UUID userId, UUID restaurantId) {
        FavoriteRestaurant fr = repo.findByUserIdAndRestaurantId(userId, restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Favorite restaurant not found: " + userId + ", " + restaurantId));
        return toResponse(fr);
    }

    public boolean verificarFavorito(UUID userId, UUID restaurantId) {
        return repo.existsByUserIdAndRestaurantId(userId, restaurantId);
    }

    public FavoriteRestaurantResponse crear(FavoriteRestaurantRequest req) {
        if (repo.existsByUserIdAndRestaurantId(req.userId(), req.restaurantId())) {
            throw new IllegalArgumentException(
                "Favorite restaurant already exists for this user and restaurant");
        }

        FavoriteRestaurant fr = new FavoriteRestaurant();
        fr.setUserId(req.userId());
        fr.setRestaurantId(req.restaurantId());
        
        return toResponse(repo.save(fr));
    }

    @Transactional
    public void eliminar(UUID userId, UUID restaurantId) {
        if (!repo.existsByUserIdAndRestaurantId(userId, restaurantId)) {
            throw new IllegalArgumentException(
                "Favorite restaurant not found: " + userId + ", " + restaurantId);
        }
        repo.deleteByUserIdAndRestaurantId(userId, restaurantId);
    }

    private FavoriteRestaurantResponse toResponse(FavoriteRestaurant fr) {
        return new FavoriteRestaurantResponse(
            fr.getUserId(),
            fr.getRestaurantId(),
            fr.getCreatedAt()
        );
    }
}