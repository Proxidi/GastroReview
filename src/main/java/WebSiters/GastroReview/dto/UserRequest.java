package WebSiters.GastroReview.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class UserRequest{
        @Email
        @NotBlank String email;
        @NotBlank String hashPassword;
}