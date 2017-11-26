package mainPackage.blocks.blocks2or1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2type.LBlock;

/**
 * Created by Inf on 2017-11-20.
 */

/*
Blok typu 2/1, w kształcie litery T


        XXX
R0       X
         X

          X
R90     XXX
          X

         X
R180     X
        XXX

        X
R270    XXX
        X
 */

public class TBlock extends AbstractBlockType2or1 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {2,0}, {1,1}, {1,2}};
    private static final int[][] shapeR90 = new int[][]{{0,1}, {1,1}, {2,0}, {2, 1}, {2, 2}};
    private static final int[][] shapeR180 = new int[][]{{1,0}, {1,1}, {0,2}, {1, 2}, {2, 2}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {0,1}, {2,1}, {0, 2}, {1, 1}};

    public TBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static TBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<TBlock> finder = new BlockFinder<>(TBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, 3, 3, board, rotation);
    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
