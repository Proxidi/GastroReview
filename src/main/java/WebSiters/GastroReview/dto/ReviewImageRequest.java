package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record ReviewImageRequest(
    @NotNull(message = "Review ID is required")
    UUID reviewId,

    @NotBlank(message = "URL is required")
    @Size(max = 500, message = "URL must not exceed 500 characters")
    String url,

    @Size(max = 200, message = "Alt text must not exceed 200 characters")
    String altText
) {}