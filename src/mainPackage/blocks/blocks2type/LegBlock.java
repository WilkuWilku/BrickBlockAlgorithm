package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;

/**
 * Created by Inf on 2017-11-18.
 */

/*
Blok typu 2, w kształcie nogi

            X
R0          X
            X
            XX

R90        XXXX
           X

           XX
R180        X
            X
            X

R270          X
           XXXX


 */


public class LegBlock extends AbstractBlockType2 implements Reducible, Blockible {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {0,2}, {0,3}, {1,3}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {2,0}, {3, 0}, {0, 1}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {1,1}, {1, 2}, {1, 3}};
    private static final int[][] shapeR270 = new int[][]{{3,0}, {1,0}, {1,1}, {2, 1}, {3, 1}};

    public LegBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static LegBlock check(int index, BoardState board, BlockRotation rotation) {
        BlockFinder<LegBlock> finder = new BlockFinder<>(LegBlock.class);
        LegBlock result = null;
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, 2, 4, board, rotation);
    }


        @Override
    public void block() {

    }

    @Override
    public void reduce() {

    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
