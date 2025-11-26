package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.AlertRequest;
import WebSiters.GastroReview.dto.AlertResponse;
import WebSiters.GastroReview.model.Alert;
import WebSiters.GastroReview.repository.AlertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlertService {

    private final AlertRepository repo;

    public AlertService(AlertRepository repo) {
        this.repo = repo;
    }

    public List<AlertResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Page<AlertResponse> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public List<AlertResponse> listarPorTipo(String type) {
        return repo.findByTypeOrderByCreatedAtDesc(type).stream()
                .map(this::toResponse).toList();
    }

    public Page<AlertResponse> listarPorTipo(String type, Pageable pageable) {
        return repo.findByType(type, pageable).map(this::toResponse);
    }

    public List<AlertResponse> listarPorRestaurante(UUID restaurantId) {
        return repo.findByRestaurantIdOrderByCreatedAtDesc(restaurantId).stream()
                .map(this::toResponse).toList();
    }

    public Page<AlertResponse> listarPorRestaurante(UUID restaurantId, Pageable pageable) {
        return repo.findByRestaurantId(restaurantId, pageable).map(this::toResponse);
    }

    public List<AlertResponse> listarPorReview(UUID reviewId) {
        return repo.findByReviewId(reviewId).stream()
                .map(this::toResponse).toList();
    }

    public Page<AlertResponse> listarPorReview(UUID reviewId, Pageable pageable) {
        return repo.findByReviewId(reviewId, pageable).map(this::toResponse);
    }

    public AlertResponse obtener(Long id) {
        Alert alert = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
        return toResponse(alert);
    }

    public AlertResponse crear(AlertRequest req) {
        // Validar que al menos uno de los IDs esté presente
        if (req.restaurantId() == null && req.reviewId() == null) {
            throw new IllegalArgumentException(
                "At least one of restaurantId or reviewId must be provided");
        }

        Alert alert = new Alert();
        alert.setType(req.type());
        alert.setRestaurantId(req.restaurantId());
        alert.setReviewId(req.reviewId());
        alert.setDetail(req.detail());
        
        return toResponse(repo.save(alert));
    }

    public AlertResponse actualizar(Long id, AlertRequest req) {
        Alert existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));

        // Validar que al menos uno de los IDs esté presente
        if (req.restaurantId() == null && req.reviewId() == null) {
            throw new IllegalArgumentException(
                "At least one of restaurantId or reviewId must be provided");
        }

        existing.setType(req.type());
        existing.setRestaurantId(req.restaurantId());
        existing.setReviewId(req.reviewId());
        existing.setDetail(req.detail());
        return toResponse(repo.save(existing));
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Alert not found: " + id);
        }
        repo.deleteById(id);
    }

    private AlertResponse toResponse(Alert alert) {
        return new AlertResponse(
            alert.getId(),
            alert.getType(),
            alert.getRestaurantId(),
            alert.getReviewId(),
            alert.getDetail(),
            alert.getCreatedAt()
        );
    }
}