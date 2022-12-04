package magneticcode.boxback.services;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.packer.bruteforce.BruteForcePackager;
import com.github.skjolber.packing.packer.laff.LargestAreaFitFirstPackager;
import com.github.skjolber.packing.packer.plain.PlainPackager;
import magneticcode.boxback.Block;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackagingServiceImpl implements PackagingService {
    @Override
    public List<Block> solve(List<Block> toPackage, List<Integer> params) {
        Container container = Container.newBuilder()
                .withMaxLoadWeight(1)
                .withEmptyWeight(0)
                .withSize(params.get(0), params.get(1), params.get(2) * 2)
                .build();
        LargestAreaFitFirstPackager packager = LargestAreaFitFirstPackager.newBuilder()
                .withContainers(container)
                .build();

        List<StackableItem> items = new ArrayList<>(toPackage.size());
        for (int i = 0; i < toPackage.size(); i++) {
            items.add(asStackableItem(toPackage.get(i), i));
        }

        Container match = packager.pack(items);

        for (StackPlacement placement : match.getStack().getPlacements()) {
            int i = Integer.parseInt(placement.getStackable().getId(), 16);

            StackValue stackedElement = placement.getStackValue();

            toPackage.get(i).zero = new int[]{placement.getAbsoluteX(), placement.getAbsoluteY(), placement.getAbsoluteZ()};
            toPackage.get(i)
                    .setParams(new int[]{stackedElement.getDx(), stackedElement.getDy(), stackedElement.getDz()});
        }
        return toPackage;
    }

    @Override
    public List<Block> plain(List<Block> toPackage, List<Integer> params) {
        Container container = Container.newBuilder()
                .withMaxLoadWeight(1)
                .withEmptyWeight(0)
                .withSize(params.get(0), params.get(1), params.get(2) * 2)
                .build();
        PlainPackager packager = PlainPackager.newBuilder().withContainers(container).build();

        List<StackableItem> items = new ArrayList<>(toPackage.size());
        for (int i = 0; i < toPackage.size(); i++) {
            items.add(asStackableItem(toPackage.get(i), i));
        }

        Container match = packager.pack(items);

        for (StackPlacement placement : match.getStack().getPlacements()) {
            int i = Integer.parseInt(placement.getStackable().getId(), 16);

            StackValue stackedElement = placement.getStackValue();

            toPackage.get(i).zero = new int[]{placement.getAbsoluteX(), placement.getAbsoluteY(), placement.getAbsoluteZ()};
            toPackage.get(i)
                    .setParams(new int[]{stackedElement.getDx(), stackedElement.getDy(), stackedElement.getDz()});
        }
        return toPackage;
    }

    @Override
    public List<Block> brute(List<Block> toPackage, List<Integer> params) {
        Container container = Container.newBuilder()
                .withMaxLoadWeight(1)
                .withEmptyWeight(0)
                .withSize(params.get(0), params.get(1), params.get(2) * 2)
                .build();
        BruteForcePackager packager = BruteForcePackager.newBuilder().withContainers(container).build();

        List<StackableItem> items = new ArrayList<>(toPackage.size());
        for (int i = 0; i < toPackage.size(); i++) {
            items.add(asStackableItem(toPackage.get(i), i));
        }

        Container match = packager.pack(items);

        for (StackPlacement placement : match.getStack().getPlacements()) {
            int i = Integer.parseInt(placement.getStackable().getId(), 16);

            StackValue stackedElement = placement.getStackValue();

            toPackage.get(i).zero = new int[]{placement.getAbsoluteX(), placement.getAbsoluteY(), placement.getAbsoluteZ()};
            toPackage.get(i)
                    .setParams(new int[]{stackedElement.getDx(), stackedElement.getDy(), stackedElement.getDz()});
        }
        return toPackage;
    }

    public StackableItem asStackableItem(Block block, int listIndex) {
        int[] params = block.getParams();
        return new StackableItem(Box.newBuilder()
                .withSize(params[0], params[1], params[2])
                .withWeight(0)
                .withId(Integer.toHexString(listIndex))
                .withRotate3D()
                .build(), 1);
    }
}
