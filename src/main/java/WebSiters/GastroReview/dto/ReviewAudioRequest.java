package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewAudioRequest(
    @NotNull(message = "Review ID is required")
    UUID reviewId,

    @NotBlank(message = "URL is required")
    String url,

    @Min(value = 0, message = "Duration must be non-negative")
    Integer durationSeconds,

    String transcription
) {}