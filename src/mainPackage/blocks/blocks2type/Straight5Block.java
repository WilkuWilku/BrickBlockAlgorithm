package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;

/**
 * Created by Inf on 2017-11-18.
 */
/*
Blok typu 2, prosty, 5-komórkowy

R0    XXXXX

      X
R90   X
      X
      X
      X

 */

public class Straight5Block extends AbstractBlockType2 implements Reducible {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {4,0}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {0,1}, {0,2}, {0,3}, {0, 4}};

    public Straight5Block(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
    }

    public static Straight5Block check(int index, BoardState board, BlockRotation rotation){
        BlockFinder<Straight5Block> finder = new BlockFinder<>(Straight5Block.class);
        return finder.find(index, shapeR0, shapeR90, null, null, 5, 1, board, rotation);
    }

    @Override
    public void reduce() {

    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
