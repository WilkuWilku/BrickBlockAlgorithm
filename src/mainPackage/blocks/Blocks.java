package mainPackage.blocks;

import mainPackage.BoardState;
import mainPackage.IndexConverter;

/**
 * Created by Inf on 2017-11-26.
 */
public final class Blocks {

    private Blocks(){}

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
