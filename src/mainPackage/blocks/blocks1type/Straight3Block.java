package mainPackage.blocks.blocks1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2type.LBlock;

/**
 * Created by Inf on 2017-12-09.
 */
public class Straight3Block extends AbstractBlockType1 {
    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {0,2}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {2,0}};

    public Straight3Block(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
    }

    public static Straight3Block check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<Straight3Block> finder = new BlockFinder<>(Straight3Block.class);
        return finder.find(index, shapeR0, shapeR90, null, null, 3, 3, board, rotation);
    }
}
