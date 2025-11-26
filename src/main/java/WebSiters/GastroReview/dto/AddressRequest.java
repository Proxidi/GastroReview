package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;

public record AddressRequest(
    @NotBlank(message = "Street is required")
    String street,

    String site,

    String neighborhood,

    String city,

    String stateRegion,

    String postalCode,

    @NotBlank(message = "Country is required")
    String country,

    Double latitude,

    Double longitude
) {}