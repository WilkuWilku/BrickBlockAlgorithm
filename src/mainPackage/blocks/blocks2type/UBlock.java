package mainPackage.blocks.blocks2type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-19.
 */
/*
Blok typu 2, w kształcie litery U


R0      X X
        XXX

        XX
R90     X
        XX

        XXX
R180    X X


        XX
R270     X
        XX
 */

public class UBlock extends AbstractBlockType2 implements Reducible, Blockible {

    private static final int[][] shapeR0 = new int[][]{{0,0}, {2,0}, {0,1}, {1,1}, {2,1}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {1,0}, {0,1}, {0, 2}, {1, 2}};
    private static final int[][] shapeR180 = new int[][]{{0,0}, {1,0}, {2,0}, {0, 1}, {2, 1}};
    private static final int[][] shapeR270 = new int[][]{{0,0}, {1,0}, {1,1}, {0, 2}, {1, 2}};

    public UBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
            case R180: shape = shapeR180; break;
            case R270: shape = shapeR270; break;
        }
    }

    public static UBlock check(int index, BoardState board, BlockRotation rotation) {

        BlockFinder<UBlock> finder = new BlockFinder<>(UBlock.class);
        return finder.find(index, shapeR0, shapeR90, shapeR180, shapeR270, Block2Types.UBlock, board, rotation);
    }


    @Override
    public void block() {

    }

    @Override
    public void reduce() {

    }


    @Override
    public BrickBlock nextMove(BoardState board) {
        switch (rotation){
            case R0:
            case R90:
            case R180: return new BrickBlock(referenceCellIndex, BlockRotation.R90, board);
            case R270: return new BrickBlock(referenceCellIndex, BlockRotation.R0, board);
            default: return null;
        }
    }
}
