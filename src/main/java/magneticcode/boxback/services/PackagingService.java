package magneticcode.boxback.services;

import magneticcode.boxback.Block;

import java.util.List;


public interface PackagingService {
    List<Block> solve(List<Block> toPackage, List<Integer> params);
}
