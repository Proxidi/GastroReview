package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Request DTO for creating/updating text analysis records.
 * - reviewId is required on create.
 * - sentiment is optional but validated by service/controller if provided.
 * - score, spamProb as BigDecimal to preserve DB precision.
 * - metadata as JSON (JsonNode).
 */
@Data
public class TextAnalysisRequest {

    @NotNull(message = "reviewId is required")
    private UUID reviewId;

    private String sentiment;

    private BigDecimal score;

    private BigDecimal spamProb;

    private String language;

    private JsonNode metadata;
}
