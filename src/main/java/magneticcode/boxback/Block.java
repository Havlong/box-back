package magneticcode.boxback;

import magneticcode.boxback.dto.BlockDTO;

import java.util.List;

public class Block {
    private final long id;
    int[] params = new int[3];
    int[] zero = new int[3];

    public Block(long id, List<Integer> params) {
        this.id = id;
        this.params[0] = params.get(0);
        this.params[1] = params.get(1);
        this.params[2] = params.get(2);
    }

    public BlockDTO asDTO() {
        assert zero.length == 3;
        assert params.length == 3;
        return new BlockDTO(id, List.of(params[0], params[1], params[2]), List.of(zero[0], zero[1], zero[2]));
    }
}
