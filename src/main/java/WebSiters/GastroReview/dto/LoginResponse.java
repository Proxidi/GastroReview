package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String type = "Bearer";
    private String email;
    private String userId;

    public LoginResponse(String token, String email, String userId) {
        this.token = token;
        this.email = email;
        this.userId = userId;
    }
}
