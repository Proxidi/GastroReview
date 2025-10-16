package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
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
