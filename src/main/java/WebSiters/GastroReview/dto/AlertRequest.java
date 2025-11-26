package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record AlertRequest(
    @NotBlank(message = "Type is required")
    @Pattern(regexp = "spam|inappropriate|fake|other", 
             message = "Type must be: spam, inappropriate, fake, or other")
    String type,

    UUID restaurantId,

    UUID reviewId,

    @Size(max = 1000, message = "Detail must not exceed 1000 characters")
    String detail
) {}