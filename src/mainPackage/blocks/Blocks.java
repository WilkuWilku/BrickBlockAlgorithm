package mainPackage.blocks;

import mainPackage.BoardState;
import mainPackage.IndexConverter;

/**
 * Created by Inf on 2017-11-26.
 */
public final class Blocks {

    private Blocks(){}

    public static boolean isEmpty(BoardState board, int index) {
        if(!board.getCell(index+board.size) || !board.getCell(index-board.size))
            return false;
        if(IndexConverter.xOfIndex(index, board.size) != 0){
            if(!board.getCell(index-1))
                return false;
        }
        if(IndexConverter.xOfIndex(index, board.size) != board.size-1){
            if(!board.getCell(index+1))
                return false;
        }
        return true;
    }

    public static int movesReductionIfSet(int index, BoardState board){
        int x = IndexConverter.xOfIndex(index, board.size);
        int y = IndexConverter.yOfIndex(index, board.size);
        int reduction = 0;
        if(!board.getCell(x+1, y))
            reduction++;
        if(!board.getCell(x-1, y))
            reduction++;
        if(!board.getCell(x, y+1))
            reduction++;
        if(!board.getCell(x, y-1))
            reduction++;
        return reduction;
    }

}
