package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.Map;
import java.util.UUID;

public record NotificationRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "friendship|comment|rating|achievement|favorite|alert|other",
             message = "Type must be: friendship, comment, rating, achievement, favorite, alert, or other")
    String type,

    @NotBlank(message = "Message is required")
    String message,

    Boolean read,

    UUID referenceId,

    Map<String, Object> metadata
) {}