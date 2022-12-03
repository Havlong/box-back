package magneticcode.boxback;

import magneticcode.boxback.dto.BlockDTO;

import java.util.List;
import java.util.stream.Stream;

public class Block {
    private final long id;
    public int[] zero = new int[3];
    private int[] params = new int[3];

    public Block(long id, List<Integer> params) {
        this.id = id;
        this.params[0] = params.get(0);
        this.params[1] = params.get(1);
        this.params[2] = params.get(2);
    }

    public int[] getParams() {
        return params;
    }

    public void setParams(int[] params) {
        if (!Stream.of(params[0], params[1], params[2])
                .sorted()
                .toList()
                .equals(Stream.of(this.params[0], this.params[1], this.params[2]).sorted().toList())) return;
        this.params = params;
    }

    public BlockDTO asDTO() {
        if (zero.length != 3 || params.length != 3) throw new IllegalStateException("Arrays are non-3D");
        return new BlockDTO(id, List.of(params[0], params[1], params[2]), List.of(zero[0], zero[1], zero[2]));
    }
}
