package mainPackage.blocks.blocks1type;

import mainPackage.BoardState;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks2type.LBlock;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-12-09.
 */

/*
Blok typu 1, w kształcie cegły

R0:     XX

R90:    X
        X
 */

public class BrickBlock extends AbstractBlockType1 {
    private static final int[][] shapeR0 = new int[][]{{0,0}, {1,0}};
    private static final int[][] shapeR90 = new int[][]{{0,0}, {0,1}};
    private ArrayList<Integer> cells;

    public BrickBlock(int referenceCellIndex, BlockRotation rotation, int boardSize){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        this.cells = new ArrayList<>();
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
        for(int i=0; i<shape.length; i++)
            cells.add(referenceCellIndex+shape[i][1]*boardSize+shape[i][0]);

    }

    public static BrickBlock checkAndCreate(int index, BoardState board, BlockRotation rotation) {
        BrickBlock result = null;
        switch (rotation){
            case R0: result = findWithRotation(index, board, shapeR0, rotation); break;
            case R90: result = findWithRotation(index, board, shapeR90, rotation); break;
        }
        return result;
    }

    private static BrickBlock findWithRotation(int index, BoardState board, int[][] shape, BlockRotation rotation){
        for(int i=0; i<shape.length; i++) {
            if (board.getCell(index + shape[i][1] * board.size + shape[i][0]) == 0)
                return null;
        }
        return new BrickBlock(index, rotation, board.size);
    }

}
