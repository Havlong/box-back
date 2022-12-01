package magneticcode.boxback.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InputDTO(@JsonProperty("productList") List<ProductDTO> productList,
                       @JsonProperty("tara_length") Integer length, @JsonProperty("tara_width") Integer width,
                       @JsonProperty("tara_height") Integer height) {

    @JsonIgnore
    public List<Integer> getDimensions3D() {
        return List.of(length, width, height);
    }
}
