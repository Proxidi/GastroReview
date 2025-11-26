package WebSiters.GastroReview.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantRequest{
        @NotBlank public String name;
        public String description;
        public String phone;
        public String email;
        public UUID ownerId;
}
