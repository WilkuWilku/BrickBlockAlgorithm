package mainPackage.blocks.blocks2or1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-20.
 */

/*
Blok typu 2/1, w kształcie odwróconej krótkiej

          X
R0        X
         XX

          X
R90       XXX

          XX
R180      X
          X

R270    XXX
          X


 */

public class InvertedShortLegBlock extends AbstractBlockType2or1 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {0,2}, {-1,2}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {0,1}, {1,1}, {2, 1}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {0,1}, {0, 2}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {1,0}, {2,0}, {2, 1}};

    public InvertedShortLegBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static InvertedShortLegBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<InvertedShortLegBlock> finder = new BlockFinder<>(InvertedShortLegBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, Block2or1Types.InvertedShortLegBlock, board, rotation);
    }

    @Override
    public BrickBlock leaveZeroMoves(BoardState board) {
        switch (rotation){
            case R0: return new BrickBlock(referenceCellIndex+board.size, BlockRotation.R90, board);
            case R90: return new BrickBlock(referenceCellIndex+board.size, BlockRotation.R0, board);
            case R180: return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
            case R270: return new BrickBlock(referenceCellIndex+1, BlockRotation.R0, board);
            default: return null;
        }
    }

    @Override
    public BrickBlock leaveOneMove(BoardState board) {
        switch (rotation){
            case R0:
            case R90: return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
            case R180:
            case R270: return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
            default: return null;
        }
    }
}
