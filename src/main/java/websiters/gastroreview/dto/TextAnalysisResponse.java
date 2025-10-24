package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO for TextAnalysis.
 */
@Value
@Builder
public class TextAnalysisResponse {
    Long id;
    UUID reviewId;
    String sentiment;
    BigDecimal score;
    BigDecimal spamProb;
    String language;
    JsonNode metadata;
    OffsetDateTime createdAt;
}
