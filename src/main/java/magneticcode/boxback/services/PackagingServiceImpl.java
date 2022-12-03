package magneticcode.boxback.services;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.packer.laff.LargestAreaFitFirstPackager;
import magneticcode.boxback.Block;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackagingServiceImpl implements PackagingService {
    @Override
    public List<Block> solve(List<Block> toPackage, List<Integer> params) {
        Container container = Container.newBuilder().withMaxLoadWeight(1).withEmptyWeight(0).withSize(params.get(0), params.get(1), params.get(2)).build();
        LargestAreaFitFirstPackager packager = LargestAreaFitFirstPackager.newBuilder().withContainers(List.of(container)).build();

        List<StackableItem> items = toPackage.stream().map(this::asStackableItem).toList();
        Container match = packager.pack(items);

        for (StackPlacement placement : match.getStack().getPlacements()) {
            String idToUse = placement.getStackable().getId();
            int i = 0;
            while (i < toPackage.size() && !toPackage.get(i).getId().equals(idToUse)) {
                i++;
            }

            StackValue stackedElement = placement.getStackValue();

            toPackage.get(i).zero = new int[]{placement.getAbsoluteX(), placement.getAbsoluteY(), placement.getAbsoluteZ()};
            toPackage.get(i).setParams(new int[]{stackedElement.getDx(), stackedElement.getDy(), stackedElement.getDz()});
        }
        return toPackage;
    }

    public StackableItem asStackableItem(Block block) {
        return new StackableItem(Box.newBuilder().withSize(block.getParams()[0], block.getParams()[1], block.getParams()[2]).withWeight(0).withId(block.getId()).withRotate3D().build(), 1);
    }
}
