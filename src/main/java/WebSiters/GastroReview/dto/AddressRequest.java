package WebSiters.GastroReview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressRequest {
    private String street;
    private String site;
    private String neighborhood;
    private String city;
    private String stateRegion;
    private String postalCode;
    private String country;
    private Double latitude;
    private Double longitude;
}
