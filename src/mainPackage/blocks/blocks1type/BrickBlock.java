package mainPackage.blocks.blocks1type;

import mainPackage.BoardState;
import mainPackage.GameState;
import mainPackage.IndexConverter;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.Blocks;

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
    private BoardState board;
    private boolean stateChanging;
    private ArrayList<Integer> cells;
    private int movesReduction = 0;
    private GameState result = GameState.UNDEFINED;

    public BrickBlock(int referenceCellIndex, BlockRotation rotation, BoardState board){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
        this.board = board;
        this.cells = new ArrayList<>();
        switch (rotation){
            case R0: shape = shapeR0; break;
            case R90: shape = shapeR90; break;
        }
        for(int i=0; i<shape.length; i++) {
            cells.add(referenceCellIndex + shape[i][1] * board.size + shape[i][0]);
            calculateMovesReduction();
            stateChanging = checkIfIsStateChanging();
        }
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
            if (board.getCell(IndexConverter.xOfIndex(index, board.size)+shape[i][0], IndexConverter.yOfIndex(index, board.size)+shape[i][1]) == 0)
                return null;
        }
        return new BrickBlock(index, rotation, board);
    }

    public int calculateMovesReduction(){
        movesReduction = 0;
        for(Integer index : cells)
            movesReduction += Blocks.movesReductionIfSet(index, board);
        movesReduction--;
        stateChanging = checkIfIsStateChanging();
        return movesReduction;
    }

    private boolean checkIfIsStateChanging(){
        return movesReduction % 2 == 1;
    }

    public ArrayList<Integer> getCells() {
        return cells;
    }

    public boolean isStateChanging() {
        return stateChanging;
    }

    @Override
    public String toString() {
        return super.toString()+" ("+ IndexConverter.indexToXY(referenceCellIndex, board.size)+") MR: "+movesReduction+"; SC: "+stateChanging;
    }

    public int getMovesReduction() {
        return movesReduction;
    }

    public void decrementMovesReduction(){
        if(movesReduction>0)
            movesReduction--;
    }

    public GameState getResult() {
        return result;
    }

    public void setResult(GameState result) {
        this.result = result;
    }
}
