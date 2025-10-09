package WebSiters.GastroReview.dto;

import java.util.UUID;

public record ReviewImageResponse(
    Long id,
    UUID reviewId,
    String url,
    String altText
) {}