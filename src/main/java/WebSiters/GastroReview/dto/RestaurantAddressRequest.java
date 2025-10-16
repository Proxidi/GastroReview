package WebSiters.GastroReview.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class RestaurantAddressRequest {
    private UUID restaurantId;
    private Long addressId;
    private Boolean isPrimary;
    private String branchName;
}
