package mainPackage.blocks.blocks2or1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;


/**
 * Created by Inf on 2017-11-20.
 */

/*
Blok typu 2/1, w kształcie pioruna

        X
R0      XX
         X

         XX
R90     XX

 */

public class ThunderBlock extends AbstractBlockType2or1 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {0,1}, {1,1}, {1,2}};
    private static final int[][] shapeR90 = new int[][]{{1,0}, {2,0}, {0, 1}, {1, 1}};

    public ThunderBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
    }

    public static ThunderBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<ThunderBlock> finder = new BlockFinder<>(ThunderBlock.class);
        return finder.find(index, shapeR0,shapeR90, null, null, 2, 3, board, rotation);
    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
