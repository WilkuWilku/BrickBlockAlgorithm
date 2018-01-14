package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.*;


/**
 * Created by Inf on 2017-11-19.
 */
public class BoardAnalyzer {
    private BoardState board;
    private static final long TIME_LIMIT = 400;


    public BoardAnalyzer(BoardState board) {
        this.board = board;
    }

    /* znajduje wszystkie możliwe ruchy (BrickBlocki) */
    public MovesData findAllMoves(long initTime){
        MovesData movesData = new MovesData();
        for(int i=0; i<board.getCells().length; i++)
            if(board.getCell(i) > 0)
                searchMovesAtIndex(i, movesData, initTime);
        return movesData;
    }

    private void addToMovesMap(int index, BrickBlock block, MovesData movesData){
        Duo move = movesData.getMovesMap().get(index);
        if(move == null)
            movesData.getMovesMap().put(index, new Duo<>(block));
        else
            //try {
                move.insert(block);
            //} catch (Exception e) {
            //    e.printStackTrace();
            //}
    }

    private void searchMovesAtIndex(int index, MovesData movesData, long initTime) {
        //if (System.currentTimeMillis() - initTime > TIME_LIMIT)
          //  return;
        for (BlockRotation rotation : BlockRotation.values()) {
            BrickBlock result = BrickBlock.createIfPossible(index, board, rotation);
            if (result != null) {
                addToMovesMap(index, result, movesData);
                movesData.nMoves++;
            }
        }
    }


    /* przerzuca ruchy z mapy do zbioru */
    public static HashSet<BrickBlock> mapValuesToSet(HashMap<Integer, Duo<BrickBlock>> map) {
        HashSet<BrickBlock> valuesSet = new HashSet<>();
        for (Duo<BrickBlock> value : map.values()) {
            valuesSet.add(value.getLeft());
            if (value.getRight() != null)
                valuesSet.add(value.getRight());
        }
        return valuesSet;
    }

    public BoardState getBoard() {
        return board;
    }
}
