package mainPackage.blocks.blocks2or1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2type.LBlock;

/**
 * Created by Inf on 2017-11-20.
 */

/*
Bloku typu 2/1, prosty, 4-komórkowy

R0      XXXX

        X
R90     X
        X
        X
 */

public class Straight4Block extends AbstractBlockType2or1 {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {2,0}, {3,0}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {0,1}, {0,2}, {0, 3}};

    public Straight4Block(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
    }

    public static Straight4Block check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<Straight4Block> finder = new BlockFinder<>(Straight4Block.class);
        return finder.find(index, shapeR0,shapeR90, null, null, 4, 1, board, rotation);
    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
