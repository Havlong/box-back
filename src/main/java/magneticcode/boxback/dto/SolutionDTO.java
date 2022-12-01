package magneticcode.boxback.dto;

import java.util.List;

public record SolutionDTO(List<ShortProductDTO> productsPackaged, List<BlockDTO> blocks, List<Integer> palette3D,
                          Integer minimisedHeight) {

}
