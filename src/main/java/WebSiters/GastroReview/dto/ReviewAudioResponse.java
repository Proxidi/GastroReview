package WebSiters.GastroReview.dto;

import java.util.UUID;

public record ReviewAudioResponse(
    Long id,
    UUID reviewId,
    String url,
    Integer durationSeconds,
    String transcription
) {}