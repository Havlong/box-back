package magneticcode.boxback.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductParamsDTO(@JsonProperty("type") String type, @JsonProperty("quantity") Integer amount,
                               @JsonProperty("length") Integer length, @JsonProperty("width") Integer width,
                               @JsonProperty("height") Integer height) {

    @JsonIgnore
    public List<Integer> getDimensions3D() {
        return List.of(length, width, height);
    }
}
