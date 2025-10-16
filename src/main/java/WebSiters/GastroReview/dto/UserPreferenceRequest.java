package WebSiters.GastroReview.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class UserPreferenceRequest {

    private UUID userId;

    @JsonProperty("key")
    private String prefKey;

    private Map<String, Object> value;
}