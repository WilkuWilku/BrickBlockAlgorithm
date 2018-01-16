package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.*;


/**
 * Created by Inf on 2017-11-19.
 */
/* klasa analizująca planszę */
public class BoardAnalyzer {
    private BoardState board;

    public BoardAnalyzer(BoardState board) {
        this.board = board;
    }

    /* znajduje wszystkie możliwe ruchy (BrickBlocki) */
    public MovesData findAllMoves(){
        MovesData movesData = new MovesData();
        for(int i=0; i<board.getCells().length; i++)
            if(board.getCell(i) > 0)
                searchMovesAtIndex(i, movesData);
        return movesData;
    }

    /* dodaje ruch do mapy ruchów */
    private void addToMovesMap(int index, BrickBlock block, MovesData movesData){
        Duo move = movesData.getMovesMap().get(index);
        if(move == null)
            movesData.getMovesMap().put(index, new Duo<>(block));
        else
            move.insert(block);
    }

    /* sprawdza czy istnieje którykolwiek z bloków w danym indeksie */
    private void searchMovesAtIndex(int index, MovesData movesData) {
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
