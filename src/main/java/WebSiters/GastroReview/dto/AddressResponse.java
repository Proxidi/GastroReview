package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;

public record AddressResponse(
    Long id,
    String street,
    String site,
    String neighborhood,
    String city,
    String stateRegion,
    String postalCode,
    String country,
    Double latitude,
    Double longitude,
    OffsetDateTime createdAt
) {}