package mainPackage.blocks.blocks2type;


import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-18.
 */
/*
Blok typu 2 w kształcie kwadratu 2x2


R0   XX
     XX

 */


public class SquareBlock extends AbstractBlockType2 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {1,0}, {1,1}};

    public SquareBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        shape = (rotation == BlockRotation.R0) ? shapeR0 : null;
    }

    public static SquareBlock check(int index, BoardState board, BlockRotation rotation) {
        BlockFinder<SquareBlock> finder = new BlockFinder<>(SquareBlock.class);
        return finder.find(index, shapeR0, null,null, null, Block2Types.SquareBlock, board, rotation);
    }

    @Override
    public BrickBlock nextMove(BoardState board) {
        return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
    }
}
