package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewImageRequest(
    @NotNull(message = "Review ID is required")
    UUID reviewId,

    @NotBlank(message = "URL is required")
    String url,

    String altText
) {}