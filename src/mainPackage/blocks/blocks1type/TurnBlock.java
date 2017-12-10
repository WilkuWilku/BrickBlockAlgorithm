package mainPackage.blocks.blocks1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;

/**
 * Created by Inf on 2017-12-09.
 */
public class TurnBlock extends AbstractBlockType1 {
    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {1,0}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {1,1}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {1,-1}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {1,0}, {0,-1}};

    public TurnBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static TurnBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<TurnBlock> finder = new BlockFinder<>(TurnBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, 2, 2, board, rotation);
    }
}
