package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class RestaurantAddressResponse {
    private UUID restaurantId;
    private Long addressId;
    private boolean isPrimary;
    private String branchName;
}
