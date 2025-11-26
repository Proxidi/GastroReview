package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.FavoriteReviewRequest;
import WebSiters.GastroReview.dto.FavoriteReviewResponse;
import WebSiters.GastroReview.model.FavoriteReview;
import WebSiters.GastroReview.repository.FavoriteReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteReviewService {

    private final FavoriteReviewRepository repo;

    public FavoriteReviewService(FavoriteReviewRepository repo) {
        this.repo = repo;
    }

    public List<FavoriteReviewResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public List<FavoriteReviewResponse> listarPorUsuario(UUID userId) {
        return repo.findByUserId(userId).stream()
                .map(this::toResponse).toList();
    }

    public List<FavoriteReviewResponse> listarPorReview(UUID reviewId) {
        return repo.findByReviewId(reviewId).stream()
                .map(this::toResponse).toList();
    }

    public FavoriteReviewResponse obtener(UUID userId, UUID reviewId) {
        FavoriteReview fr = repo.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Favorite review not found: " + userId + ", " + reviewId));
        return toResponse(fr);
    }

    public boolean verificarFavorito(UUID userId, UUID reviewId) {
        return repo.existsByUserIdAndReviewId(userId, reviewId);
    }

    public FavoriteReviewResponse crear(FavoriteReviewRequest req) {
        if (repo.existsByUserIdAndReviewId(req.userId(), req.reviewId())) {
            throw new IllegalArgumentException(
                "Favorite review already exists for this user and review");
        }

        FavoriteReview fr = new FavoriteReview();
        fr.setUserId(req.userId());
        fr.setReviewId(req.reviewId());
        
        return toResponse(repo.save(fr));
    }

    @Transactional
    public void eliminar(UUID userId, UUID reviewId) {
        if (!repo.existsByUserIdAndReviewId(userId, reviewId)) {
            throw new IllegalArgumentException(
                "Favorite review not found: " + userId + ", " + reviewId);
        }
        repo.deleteByUserIdAndReviewId(userId, reviewId);
    }

    private FavoriteReviewResponse toResponse(FavoriteReview fr) {
        return new FavoriteReviewResponse(
            fr.getUserId(),
            fr.getReviewId(),
            fr.getCreatedAt()
        );
    }
}