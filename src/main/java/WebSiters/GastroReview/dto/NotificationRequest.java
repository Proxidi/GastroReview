package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.Map;
import java.util.UUID;

public record NotificationRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "review|favorite|alert|system|other", 
             message = "Type must be: review, favorite, alert, system, or other")
    String type,

    @NotBlank(message = "Message is required")
    @Size(max = 500, message = "Message must not exceed 500 characters")
    String message,

    Boolean read,

    UUID referenceId,

    Map<String, Object> metadata
) {}