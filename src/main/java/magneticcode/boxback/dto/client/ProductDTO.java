package magneticcode.boxback.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductDTO(@JsonProperty("productName") String name,
                         @JsonProperty("productDimensions") List<ProductParamsDTO> productDimensions) {

}
