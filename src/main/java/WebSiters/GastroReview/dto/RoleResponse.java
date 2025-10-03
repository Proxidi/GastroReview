package WebSiters.GastroReview.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder

public class RoleResponse {
    Integer id;
    String name;
    String description;
}
