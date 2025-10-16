package WebSiters.GastroReview.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class UserAchievementRequest {
    private UUID userId;
    private String badge;
    private Integer level;
    private Integer stars;
}
