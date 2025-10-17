package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.TextAnalysisRequest;
import WebSiters.GastroReview.dto.TextAnalysisResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Review;
import WebSiters.GastroReview.model.TextAnalysis;
import WebSiters.GastroReview.repository.ReviewRepository;
import WebSiters.GastroReview.repository.TextAnalysisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of TextAnalysisService.
 *
 * Responsibilities:
 * - Resolve referenced Review
 * - Validate sentiment values (allowed set)
 * - Orchestrate create/update/delete with transactions
 */
@Service
@RequiredArgsConstructor
public class TextAnalysisServiceImpl implements TextAnalysisService {

    private final TextAnalysisRepository repo;
    private final ReviewRepository reviewRepo;

    private static final Set<String> ALLOWED_SENTIMENTS = Set.of("negative", "neutral", "positive", "mixed");

    @Override
    public Page<TextAnalysisResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @Override
    public Page<TextAnalysisResponse> findByReviewId(UUID reviewId, Pageable pageable) {
        return repo.findByReview_Id(reviewId, pageable).map(Mappers::toDto);
    }

    @Override
    public TextAnalysisResponse findById(Long id) {
        TextAnalysis t = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Text analysis not found: " + id));
        return Mappers.toDto(t);
    }

    @Override
    @Transactional
    public TextAnalysisResponse create(TextAnalysisRequest req) {
        // Resolve review
        Review review = reviewRepo.findById(req.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + req.getReviewId()));

        // Validate sentiment if provided
        if (req.getSentiment() != null && !ALLOWED_SENTIMENTS.contains(req.getSentiment())) {
            throw new IllegalArgumentException("Invalid sentiment value");
        }

        // Optionally validate numeric ranges (score/spamProb) if desired — not enforced by DB beyond precision
        validateNumeric(req.getScore(), "score");
        validateNumeric(req.getSpamProb(), "spamProb");

        TextAnalysis toSave = Mappers.toEntity(req, review);
        try {
            TextAnalysis saved = repo.save(toSave);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public TextAnalysisResponse update(Long id, TextAnalysisRequest req) {
        TextAnalysis existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Text analysis not found: " + id));

        // Forbid changing the review via update
        if (req.getReviewId() != null && !req.getReviewId().equals(existing.getReview().getId())) {
            throw new IllegalArgumentException("Changing review is not allowed");
        }

        // Validate sentiment if provided
        if (req.getSentiment() != null && !ALLOWED_SENTIMENTS.contains(req.getSentiment())) {
            throw new IllegalArgumentException("Invalid sentiment value");
        }

        validateNumeric(req.getScore(), "score");
        validateNumeric(req.getSpamProb(), "spamProb");

        Mappers.updateEntityFromRequest(req, existing);

        try {
            TextAnalysis saved = repo.save(existing);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Text analysis not found: " + id);
        }
        repo.deleteById(id);
    }

    /* ---------- helpers ---------- */

    private void validateNumeric(BigDecimal value, String fieldName) {
        if (value == null) return;
        // We don't enforce a strict range here because DB only specifies precision; implement rules if needed.
        // Example: ensure scale <= 4 and precision reasonable
        if (value.scale() > 4) {
            throw new IllegalArgumentException(fieldName + " has more than 4 decimal places");
        }
    }

    private String getRootCauseMessage(DataIntegrityViolationException ex) {
        Throwable root = ex;
        while (root.getCause() != null) root = root.getCause();
        return root.getMessage() != null ? root.getMessage() : ex.getMessage();
    }
}
