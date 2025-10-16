package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class UserRoleResponse {
    private UUID userId;
    private Integer roleId;
    private OffsetDateTime assignedAt;
}
