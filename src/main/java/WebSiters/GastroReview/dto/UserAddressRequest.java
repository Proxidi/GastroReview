package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record UserAddressRequest(
    @NotNull(message = "User ID is required")
    UUID userId,

    @NotNull(message = "Address ID is required")
    Long addressId,

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "primary|shipping|billing|other", 
             message = "Type must be: primary, shipping, billing, or other")
    String type,

    @NotNull(message = "Active status is required")
    Boolean active
) {}