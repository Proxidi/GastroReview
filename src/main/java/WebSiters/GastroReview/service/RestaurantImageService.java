package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantImageRequest;
import WebSiters.GastroReview.dto.RestaurantImageResponse;
import WebSiters.GastroReview.model.RestaurantImage;
import WebSiters.GastroReview.repository.RestaurantImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantImageService {

    private final RestaurantImageRepository repo;

    public RestaurantImageService(RestaurantImageRepository repo) {
        this.repo = repo;
    }

    public List<RestaurantImageResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public List<RestaurantImageResponse> listarPorRestaurante(UUID restaurantId) {
        return repo.findByRestaurantIdOrderByCreatedAtDesc(restaurantId).stream()
                .map(this::toResponse).toList();
    }

    public List<RestaurantImageResponse> listarPorUsuario(UUID uploadedBy) {
        return repo.findByUploadedBy(uploadedBy).stream()
                .map(this::toResponse).toList();
    }

    public RestaurantImageResponse obtener(Long id) {
        RestaurantImage ri = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Restaurant image not found: " + id));
        return toResponse(ri);
    }

    public RestaurantImageResponse crear(RestaurantImageRequest req) {
        RestaurantImage ri = new RestaurantImage();
        ri.setRestaurantId(req.restaurantId());
        ri.setUrl(req.url());
        ri.setAltText(req.altText());
        ri.setUploadedBy(req.uploadedBy());
        
        return toResponse(repo.save(ri));
    }

    public RestaurantImageResponse actualizar(Long id, RestaurantImageRequest req) {
        RestaurantImage existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Restaurant image not found: " + id));

        existing.setRestaurantId(req.restaurantId());
        existing.setUrl(req.url());
        existing.setAltText(req.altText());
        existing.setUploadedBy(req.uploadedBy());
        return toResponse(repo.save(existing));
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Restaurant image not found: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public void eliminarPorRestaurante(UUID restaurantId) {
        repo.deleteByRestaurantId(restaurantId);
    }

    private RestaurantImageResponse toResponse(RestaurantImage ri) {
        return new RestaurantImageResponse(
            ri.getId(),
            ri.getRestaurantId(),
            ri.getUrl(),
            ri.getAltText(),
            ri.getUploadedBy(),
            ri.getCreatedAt()
        );
    }
}