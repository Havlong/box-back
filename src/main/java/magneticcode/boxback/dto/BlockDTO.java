package magneticcode.boxback.dto;

import java.util.List;

public record BlockDTO(Long id, List<Integer> dimensions3D, List<Integer> zeroPoint) {

}
