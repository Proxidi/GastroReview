package WebSiters.GastroReview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private UUID id;
    private String street;
    private String site;
    private String neighborhood;
    private String city;
    private String stateRegion;
    private Integer postalCode;
    private String country;
    private Double latitude;
    private Double longitude;
}
