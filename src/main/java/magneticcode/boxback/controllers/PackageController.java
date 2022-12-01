package magneticcode.boxback.controllers;

import magneticcode.boxback.Block;
import magneticcode.boxback.dto.ShortProductDTO;
import magneticcode.boxback.dto.SolutionDTO;
import magneticcode.boxback.dto.client.InputDTO;
import magneticcode.boxback.dto.client.ProductDTO;
import magneticcode.boxback.dto.client.ProductParamsDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/packaging")
public class PackageController {

    @GetMapping(path = "/solve",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public SolutionDTO solvePackaging(@RequestBody InputDTO inputDTO) {
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

        return new SolutionDTO(products, blocks.stream().map(Block::asDTO).toList(), inputDTO.getDimensions3D(), 0);
    }
}
