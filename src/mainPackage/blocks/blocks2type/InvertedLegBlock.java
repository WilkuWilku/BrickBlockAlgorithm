package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;

/**
 * Created by Inf on 2017-11-20.
 */

/*
Lustrzane odbicie bloku LegBlock

        X
R0      X
        X
       XX

        X
R90     XXXX


        XX
R180    X
        X
        X


R270    XXXX
           X

 */


public class InvertedLegBlock extends AbstractBlockType2 implements Reducible, Blockible {

    private static final int[][] shapeR0 = new int[][]{{1,0}, {1,2}, {1,3}, {0,4}, {1,4}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {0,1}, {1,1}, {2, 1}, {3, 1}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {0,1}, {0, 2}, {0, 3}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {1,0}, {2,0}, {3, 0}, {3, 1}};

    public InvertedLegBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static InvertedLegBlock check(int index, BoardState board, BlockRotation rotation) {
        BlockFinder<InvertedLegBlock> finder = new BlockFinder<>(InvertedLegBlock.class);
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
