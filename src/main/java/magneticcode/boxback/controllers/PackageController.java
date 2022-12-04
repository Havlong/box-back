package magneticcode.boxback.controllers;

import magneticcode.boxback.Block;
import magneticcode.boxback.dto.BlockDTO;
import magneticcode.boxback.dto.ShortProductDTO;
import magneticcode.boxback.dto.SolutionDTO;
import magneticcode.boxback.dto.client.InputDTO;
import magneticcode.boxback.dto.client.ProductDTO;
import magneticcode.boxback.dto.client.ProductParamsDTO;
import magneticcode.boxback.services.PackagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/packaging")
public class PackageController {

    @Autowired
    PackagingService packagingService;

    @PostMapping(path = "/solve",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public SolutionDTO solveLAFF(@RequestBody InputDTO inputDTO) {
        List<ShortProductDTO> products = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();

        for (ProductDTO productDTO : inputDTO.productList()) {
            for (ProductParamsDTO product : productDTO.productDimensions()) {
                long id = products.size();
                products.add(new ShortProductDTO(id, productDTO.name(), product.type()));
                for (int i = 0; i < product.amount(); i++) {
                    blocks.add(new Block(id, product.getDimensions3D()));
                }
            }
        }

        List<BlockDTO> blockList = packagingService.solve(blocks, inputDTO.getDimensions3D())
                .stream()
                .sorted(Comparator.comparingInt((Block block) -> block.zero[2])
                        .thenComparingInt(block -> block.zero[0] + block.zero[1])
                        .thenComparingInt(block -> block.zero[1])
                        .thenComparingInt(block -> block.zero[0]))
                .map(Block::asDTO)
                .toList();

        return new SolutionDTO(products, blockList, inputDTO.getDimensions3D());
    }

    @PostMapping(path = "/volume",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public SolutionDTO solveVolumeBased(@RequestBody InputDTO inputDTO) {
        List<ShortProductDTO> products = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();

        for (ProductDTO productDTO : inputDTO.productList()) {
            for (ProductParamsDTO product : productDTO.productDimensions()) {
                long id = products.size();
                products.add(new ShortProductDTO(id, productDTO.name(), product.type()));
                for (int i = 0; i < product.amount(); i++) {
                    blocks.add(new Block(id, product.getDimensions3D()));
                }
            }
        }

        List<BlockDTO> blockList = packagingService.plain(blocks, inputDTO.getDimensions3D())
                .stream()
                .sorted(Comparator.comparingInt((Block block) -> block.zero[2])
                        .thenComparingInt(block -> block.zero[0] + block.zero[1])
                        .thenComparingInt(block -> block.zero[1])
                        .thenComparingInt(block -> block.zero[0]))
                .map(Block::asDTO)
                .toList();

        return new SolutionDTO(products, blockList, inputDTO.getDimensions3D());
    }

    @PostMapping(path = "/brutal",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public SolutionDTO solveBF(@RequestBody InputDTO inputDTO) {
        List<ShortProductDTO> products = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();

        for (ProductDTO productDTO : inputDTO.productList()) {
            for (ProductParamsDTO product : productDTO.productDimensions()) {
                long id = products.size();
                products.add(new ShortProductDTO(id, productDTO.name(), product.type()));
                for (int i = 0; i < product.amount(); i++) {
                    blocks.add(new Block(id, product.getDimensions3D()));
                }
            }
        }
        if (blocks.size() > 8) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Don't Use so much blocks");

        List<BlockDTO> blockList = packagingService.brute(blocks, inputDTO.getDimensions3D())
                .stream()
                .sorted(Comparator.comparingInt((Block block) -> block.zero[2])
                        .thenComparingInt(block -> block.zero[0] + block.zero[1])
                        .thenComparingInt(block -> block.zero[1])
                        .thenComparingInt(block -> block.zero[0]))
                .map(Block::asDTO)
                .toList();

        return new SolutionDTO(products, blockList, inputDTO.getDimensions3D());
    }
}
