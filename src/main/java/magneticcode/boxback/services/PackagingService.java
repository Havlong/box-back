package magneticcode.boxback.services;

import magneticcode.boxback.Block;

import java.util.List;


public interface PackagingService {
    List<Block> solve(List<Block> toPackage, List<Integer> params);

    List<Block> plain(List<Block> toPackage, List<Integer> params);

    List<Block> brute(List<Block> toPackage, List<Integer> params);
}
