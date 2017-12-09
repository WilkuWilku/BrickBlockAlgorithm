package mainPackage.blocks.blocks1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2type.LBlock;

/**
 * Created by Inf on 2017-12-09.
 */

/*
Blok typu 1, w kształcie pinezki

R0:          XXX
              X

              X
R90:         XX
              X

              X
R180         XXX

              X
R270          XX
              X
 */

public class PinBlock extends AbstractBlockType1 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {2,0}, {1,1}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {1,-1}, {1, 1}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {2, 0}, {1, -1}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {0,1}, {0,2}, {1, 1}};

    public PinBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static PinBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<PinBlock> finder = new BlockFinder<>(PinBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, 3, 3, board, rotation);
    }
}
