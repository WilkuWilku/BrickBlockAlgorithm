package mainPackage.blocks;

import mainPackage.BoardState;
import mainPackage.IndexConverter;

/**
 * Created by Inf on 2017-11-26.
 */
public final class Blocks {

    private Blocks(){}

    public static boolean isEmpty(BoardState board, int index) {
        if(board.getCell(index+board.size) != 0 || board.getCell(index-board.size) != 0)
            return false;
        if(IndexConverter.xOfIndex(index, board.size) != 0){
            if(board.getCell(index-1) != 0)
                return false;
        }
        if(IndexConverter.xOfIndex(index, board.size) != board.size-1){
            if(board.getCell(index+1) != 0)
                return false;
        }
        return true;
    }

    public static int movesReductionIfSet(int index, BoardState board){
        int x = IndexConverter.xOfIndex(index, board.size);
        int y = IndexConverter.yOfIndex(index, board.size);
        int reduction = 0;
        if(board.getCell(x+1, y) != 0)
            reduction++;
        if(board.getCell(x-1, y) != 0)
            reduction++;
        if(board.getCell(x, y+1) != 0)
            reduction++;
        if(board.getCell(x, y-1) != 0)
            reduction++;
        return reduction;
    }

}
