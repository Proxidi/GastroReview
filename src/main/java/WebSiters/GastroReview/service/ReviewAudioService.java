package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.ReviewAudioRequest;
import WebSiters.GastroReview.dto.ReviewAudioResponse;
import WebSiters.GastroReview.model.ReviewAudio;
import WebSiters.GastroReview.repository.ReviewAudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewAudioService {

    private final ReviewAudioRepository repo;

    public ReviewAudioService(ReviewAudioRepository repo) {
        this.repo = repo;
    }

    public List<ReviewAudioResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public List<ReviewAudioResponse> listarPorReview(UUID reviewId) {
        return repo.findByReviewId(reviewId).stream()
                .map(this::toResponse).toList();
    }

    public ReviewAudioResponse obtener(Long id) {
        ReviewAudio ra = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Review audio not found: " + id));
        return toResponse(ra);
    }

    public ReviewAudioResponse crear(ReviewAudioRequest req) {
        ReviewAudio ra = new ReviewAudio();
        ra.setReviewId(req.reviewId());
        ra.setUrl(req.url());
        ra.setDurationSeconds(req.durationSeconds());
        ra.setTranscription(req.transcription());
        
        return toResponse(repo.save(ra));
    }

    public ReviewAudioResponse actualizar(Long id, ReviewAudioRequest req) {
        ReviewAudio existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Review audio not found: " + id));

        existing.setReviewId(req.reviewId());
        existing.setUrl(req.url());
        existing.setDurationSeconds(req.durationSeconds());
        existing.setTranscription(req.transcription());
        return toResponse(repo.save(existing));
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Review audio not found: " + id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public void eliminarPorReview(UUID reviewId) {
        repo.deleteByReviewId(reviewId);
    }

    private ReviewAudioResponse toResponse(ReviewAudio ra) {
        return new ReviewAudioResponse(
            ra.getId(),
            ra.getReviewId(),
            ra.getUrl(),
            ra.getDurationSeconds(),
            ra.getTranscription()
        );
    }
}