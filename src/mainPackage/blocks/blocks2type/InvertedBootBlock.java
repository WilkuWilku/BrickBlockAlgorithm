package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-20.
 */
/*
Lustrzane odbicie bloku BootBlock

        XX
R0     XXX


        X
R90     XX
        XX


R180    XXX
        XX

        XX
R270    XX
         X

 */


public class InvertedBootBlock extends AbstractBlockType2  {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {-1,1}, {0,1}, {1,1}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,1}, {1,2}, {0, 1}, {0, 2}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {2,0}, {1, 1}, {0, 1}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {1,0}, {0,1}, {1, 1}, {1, 2}};

    public InvertedBootBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static InvertedBootBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<InvertedBootBlock> finder = new BlockFinder<>(InvertedBootBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, Block2Types.InvertedBootBlock, board, rotation);
    }

    @Override
    public BrickBlock nextMove(BoardState board) {
        switch (rotation){
            case R0: return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
            case R90: return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
            case R180: return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
            case R270: return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
            default: return null;
        }
    }
}
