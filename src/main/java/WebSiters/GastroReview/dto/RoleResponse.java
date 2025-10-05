package WebSiters.GastroReview.dto;


import lombok.Builder;
import lombok.Value;


import java.util.UUID;

@Value
@Builder

public class RoleResponse {
     Integer id;
     String name;
     String description;
}
