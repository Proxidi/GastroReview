package WebSiters.GastroReview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import WebSiters.GastroReview.dto.TextAnalysisRequest;
import WebSiters.GastroReview.dto.TextAnalysisResponse;

import java.util.UUID;

/**
 * Service API for TextAnalysis operations.
 */
public interface TextAnalysisService {

    Page<TextAnalysisResponse> findAll(Pageable pageable);

    Page<TextAnalysisResponse> findByReviewId(UUID reviewId, Pageable pageable);

    TextAnalysisResponse findById(Long id);

    TextAnalysisResponse create(TextAnalysisRequest req);

    TextAnalysisResponse update(Long id, TextAnalysisRequest req);

    void delete(Long id);
}
