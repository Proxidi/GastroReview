package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.ReviewImageRequest;
import WebSiters.GastroReview.dto.ReviewImageResponse;
import WebSiters.GastroReview.model.ReviewImage;
import WebSiters.GastroReview.repository.ReviewImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewImageService {

    private final ReviewImageRepository repo;

    public ReviewImageService(ReviewImageRepository repo) {
        this.repo = repo;
    }

    public List<ReviewImageResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public List<ReviewImageResponse> listarPorReview(UUID reviewId) {
        return repo.findByReviewId(reviewId).stream()
                .map(this::toResponse).toList();
    }

    public ReviewImageResponse obtener(Long id) {
        ReviewImage ri = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Review image not found: " + id));
        return toResponse(ri);
    }

    public ReviewImageResponse crear(ReviewImageRequest req) {
        ReviewImage ri = new ReviewImage();
        ri.setReviewId(req.reviewId());
        ri.setUrl(req.url());
        ri.setAltText(req.altText());
        
        return toResponse(repo.save(ri));
    }

    public ReviewImageResponse actualizar(Long id, ReviewImageRequest req) {
        ReviewImage existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Review image not found: " + id));

        existing.setReviewId(req.reviewId());
        existing.setUrl(req.url());
        existing.setAltText(req.altText());
        return toResponse(repo.save(existing));
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Review image not found: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public void eliminarPorReview(UUID reviewId) {
        repo.deleteByReviewId(reviewId);
    }

    private ReviewImageResponse toResponse(ReviewImage ri) {
        return new ReviewImageResponse(
            ri.getId(),
            ri.getReviewId(),
            ri.getUrl(),
            ri.getAltText()
        );
    }
}