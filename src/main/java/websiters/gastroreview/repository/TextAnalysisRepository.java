package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.TextAnalysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for TextAnalysis entity.
 */
public interface TextAnalysisRepository extends JpaRepository<TextAnalysis, Long> {
    Page<TextAnalysis> findByReview_Id(UUID reviewId, Pageable pageable);
    List<TextAnalysis> findByReview_Id(UUID reviewId);
}
