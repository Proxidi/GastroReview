package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.model.Review;
import WebSiters.GastroReview.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository repo;

    public ReviewService(ReviewRepository repo) {
        this.repo = repo;
    }

    public List<ReviewResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Page<ReviewResponse> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public List<ReviewResponse> listarPorUsuario(UUID userId) {
        return repo.findByUserIdOrderByPublishedAtDesc(userId).stream()
                .map(this::toResponse).toList();
    }

    public Page<ReviewResponse> listarPorUsuario(UUID userId, Pageable pageable) {
        return repo.findByUserId(userId, pageable).map(this::toResponse);
    }

    public List<ReviewResponse> listarPorRestaurante(UUID restaurantId) {
        return repo.findByRestaurantIdOrderByPublishedAtDesc(restaurantId).stream()
                .map(this::toResponse).toList();
    }

    public Page<ReviewResponse> listarPorRestaurante(UUID restaurantId, Pageable pageable) {
        return repo.findByRestaurantId(restaurantId, pageable).map(this::toResponse);
    }

    public List<ReviewResponse> listarPorPlatillo(UUID dishId) {
        return repo.findByDishIdOrderByPublishedAtDesc(dishId).stream()
                .map(this::toResponse).toList();
    }

    public Page<ReviewResponse> listarPorPlatillo(UUID dishId, Pageable pageable) {
        return repo.findByDishId(dishId, pageable).map(this::toResponse);
    }

    public List<ReviewResponse> listarConAudio() {
        return repo.findByHasAudioTrue().stream()
                .map(this::toResponse).toList();
    }

    public Page<ReviewResponse> listarConAudio(Pageable pageable) {
        return repo.findByHasAudioTrue(pageable).map(this::toResponse);
    }

    public List<ReviewResponse> listarConImagen() {
        return repo.findByHasImageTrue().stream()
                .map(this::toResponse).toList();
    }

    public Page<ReviewResponse> listarConImagen(Pageable pageable) {
        return repo.findByHasImageTrue(pageable).map(this::toResponse);
    }

    public ReviewResponse obtener(UUID id) {
        Review review = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + id));
        return toResponse(review);
    }

    public ReviewResponse crear(ReviewRequest req) {
        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setUserId(req.userId());
        review.setRestaurantId(req.restaurantId());
        review.setDishId(req.dishId());
        review.setTitle(req.title());
        review.setContent(req.content());
        review.setHasAudio(req.hasAudio() != null ? req.hasAudio() : false);
        review.setHasImage(req.hasImage() != null ? req.hasImage() : false);
        
        return toResponse(repo.save(review));
    }

    public ReviewResponse actualizar(UUID id, ReviewRequest req) {
        Review existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + id));

        existing.setUserId(req.userId());
        existing.setRestaurantId(req.restaurantId());
        existing.setDishId(req.dishId());
        existing.setTitle(req.title());
        existing.setContent(req.content());
        existing.setHasAudio(req.hasAudio() != null ? req.hasAudio() : existing.isHasAudio());
        existing.setHasImage(req.hasImage() != null ? req.hasImage() : existing.isHasImage());
        
        return toResponse(repo.save(existing));
    }

    public void eliminar(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Review not found: " + id);
        }
        repo.deleteById(id);
    }

    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getUserId(),
            review.getRestaurantId(),
            review.getDishId(),
            review.getTitle(),
            review.getContent(),
            review.isHasAudio(),
            review.isHasImage(),
            review.getPublishedAt()
        );
    }
}