package WebSiters.GastroReview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserPreferenceResponse {

    private Long id;

    @JsonProperty("key")
    private String prefKey;

    private Map<String, Object> value;
}
