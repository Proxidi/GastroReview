package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserAchievementResponse {
    private Long id;
    private String badge;
    private Integer level;
    private Integer stars;
}
