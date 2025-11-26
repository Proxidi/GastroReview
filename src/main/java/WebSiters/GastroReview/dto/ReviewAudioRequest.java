package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewAudioRequest(
    @NotNull(message = "Review ID is required")
    UUID reviewId,

    @NotBlank(message = "URL is required")
    @Size(max = 500, message = "URL must not exceed 500 characters")
    String url,

    @Min(value = 0, message = "Duration must be greater than or equal to 0")
    Integer durationSeconds,

    @Size(max = 5000, message = "Transcription must not exceed 5000 characters")
    String transcription
) {}