package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-18.
 */

/*
Blok typu 2, w kształcie przedłużonego pioruna

         X
        XX
R0      XX
        X

       XXX
R90     XXX


 */

public class LongThunderBlock extends AbstractBlockType2 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {0,1}, {1,1}, {0,2}, {1,-1}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {2,0}, {1, 1}, {2, 1}, {3,1}};

    public LongThunderBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
    }

    public static LongThunderBlock check(int index, BoardState board, BlockRotation rotation) {
        BlockFinder<LongThunderBlock> finder = new BlockFinder<>(LongThunderBlock.class);
        return finder.find(index, shapeR0, shapeR90, null, null, Block2Types.LongThunderBlock, board, rotation);
    }

    @Override
    public BrickBlock nextMove(BoardState board) {
        return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
    }
}
