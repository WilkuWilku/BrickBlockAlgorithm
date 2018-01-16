package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-19.
 */
/*
Blok typu 2, w kształcie schodów

        X
R0      XX
         XX

         XX
R90     XX
        X

        XX
R180     XX
          X

          X
R270     XX
        XX

 */


public class StairsBlock extends AbstractBlockType2 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {1,1}, {1,2}, {2,2}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {-1,1}, {0, 1}, {-1, 2}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {1,1}, {2, 1}, {2, 2}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {-1,1}, {0,1}, {-2, 2}, {-1, 2}};

    public StairsBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static StairsBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<StairsBlock> finder = new BlockFinder<>(StairsBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, Block2Types.StairsBlock, board, rotation);
    }

    @Override
    public BrickBlock nextMove(BoardState board) {
        switch (rotation){
            case R0:
            case R90:
            case R270: return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
            case R180: return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
            default: return null;
        }
    }
}
