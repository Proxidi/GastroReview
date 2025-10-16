package WebSiters.GastroReview.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class UserRoleRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private Integer roleId;

}

