package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-18.
 */

/*
Blok typu 2, w kształcie litery S

        XX
R0      X
       XX

        X
R90     XXX
          X

 */

public class SBlock extends AbstractBlockType2 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {0,1}, {-1,2}, {0,2}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {0,1}, {1,1}, {2, 1}, {2, 2}};

    public SBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
    }

    public static SBlock check(int index, BoardState board, BlockRotation rotation) {
        BlockFinder<SBlock> finder = new BlockFinder<>(SBlock.class);
        return finder.find(index, shapeR0, shapeR90, null, null, Block2Types.SBlock, board, rotation);
    }

    @Override
    public BrickBlock nextMove(BoardState board) {
        return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
    }
}
