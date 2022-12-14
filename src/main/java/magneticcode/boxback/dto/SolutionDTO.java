package magneticcode.boxback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SolutionDTO(List<ShortProductDTO> productsPackaged, List<BlockDTO> blocks, List<Integer> palette3D) {
    private static Long getVolume(List<Integer> dimensions3D) {
        if (dimensions3D.size() != 3) throw new IllegalArgumentException();
        return (long) dimensions3D.get(0) * dimensions3D.get(1) * dimensions3D.get(2);
    }

    @JsonProperty("minimizedHeight")
    Integer getMinimizedHeight() {
        return blocks.stream()
                .mapToInt(blockDTO -> blockDTO.zeroPoint().get(2) + blockDTO.dimensions3D().get(2))
                .max()
                .orElse(0);
    }

    @JsonProperty("emptySpace")
    Long getEmptySpace() {
        return getPaletteVolume() - blocks().stream()
                .map(BlockDTO::dimensions3D)
                .mapToLong(SolutionDTO::getVolume)
                .sum();
    }

    @JsonProperty("paletteVolume")
    Long getPaletteVolume() {
        return (long) getMinimizedHeight() * palette3D().get(0) * palette3D.get(1);
    }
}
