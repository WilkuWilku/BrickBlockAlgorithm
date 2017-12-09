package mainPackage.blocks.blocks1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2type.LBlock;

/**
 * Created by Inf on 2017-12-09.
 */
public class PlusBlock extends AbstractBlockType1 {
    private static final int[][] shapeR0 = new int[][]{{0,0}, {-1,0}, {1,0}, {0,-1}, {0,1}};

    public PlusBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        this.shape = shapeR0;
    }

    public static PlusBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<PlusBlock> finder = new BlockFinder<>(PlusBlock.class);
        return finder.find(index, shapeR0, null, null, null, 3, 3, board, rotation);
    }
}
