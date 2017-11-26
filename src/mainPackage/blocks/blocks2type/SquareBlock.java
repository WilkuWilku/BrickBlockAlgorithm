package mainPackage.blocks.blocks2type;


import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;

/**
 * Created by Inf on 2017-11-18.
 */
/*
Blok typu 2 w kształcie kwadratu 2x2


R0   XX
     XX

 */


public class SquareBlock extends AbstractBlockType2 implements Blockible{

    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {1,0}, {1,1}};

    public SquareBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        shape = shapeR0;

    }

    public static SquareBlock check(int index, BoardState board, BlockRotation rotation) {
        BlockFinder<SquareBlock> finder = new BlockFinder<>(SquareBlock.class);
        return finder.find(index, shapeR0, null,null, null, 2, 2, board, rotation);
    }


        @Override
    public void block() {

    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
