package WebSiters.GastroReview.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data

public class UserProfileRequest {
    UUID userId;
    String photoUrl;
    String name;
    Boolean active;
    String bio;
}