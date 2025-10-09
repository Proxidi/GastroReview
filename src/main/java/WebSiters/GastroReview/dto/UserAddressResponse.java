package WebSiters.GastroReview.dto;

import java.util.UUID;

public record UserAddressResponse(
    UUID userId,
    Long addressId,
    String type,
    Boolean active
) {}