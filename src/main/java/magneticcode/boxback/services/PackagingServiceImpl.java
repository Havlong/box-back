package magneticcode.boxback.services;

import com.github.skjolber.packing.api.Box;
import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackPlacement;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.packer.laff.LargestAreaFitFirstPackager;
import magneticcode.boxback.Block;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackagingServiceImpl implements PackagingService {
    @Override
    public List<Block> solve(List<Block> toPackage, List<Integer> params) {
        Container container = Container.newBuilder().withMaxLoadWeight(1).withEmptyWeight(0).withSize(params.get(0), params.get(1), params.get(2)).build();
        LargestAreaFitFirstPackager packager = LargestAreaFitFirstPackager.newBuilder().withContainers(container).build();

        List<StackableItem> items = toPackage.stream().map(this::asStackableItem).toList();
        Container match = packager.pack(items);

        for (StackPlacement placement : match.getStack().getPlacements()) {
            String idToUse = placement.getStackable().getId();
            int i = 0;
            while (i < toPackage.size() && !toPackage.get(i).getId().equals(idToUse)) {
                i++;
            }

            toPackage.get(i).zero = new int[]{placement.getAbsoluteX(), placement.getAbsoluteY(), placement.getAbsoluteZ()};
            toPackage.get(i).setParams(new int[]{placement.getAbsoluteEndX() - placement.getAbsoluteX(), placement.getAbsoluteEndY() - placement.getAbsoluteY(), placement.getAbsoluteEndZ() - placement.getAbsoluteZ()});
        }
        return toPackage;
    }

    public StackableItem asStackableItem(Block block) {
        return new StackableItem(Box.newBuilder().withSize(block.zero[0], block.zero[1], block.zero[2]).withWeight(0).withId(block.getId()).withRotate3D().build());
    }
}
