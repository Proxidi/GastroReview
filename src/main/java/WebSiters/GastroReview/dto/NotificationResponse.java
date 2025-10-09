package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record NotificationResponse(
    Long id,
    UUID userId,
    String type,
    String message,
    Boolean read,
    UUID referenceId,
    Map<String, Object> metadata,
    OffsetDateTime createdAt
) {}