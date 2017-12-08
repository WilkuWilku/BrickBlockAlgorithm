package mainPackage.blocks;

import mainPackage.BoardState;
import mainPackage.IndexConverter;

/**
 * Created by Inf on 2017-11-26.
 */
public class EmptyBlock extends AbstractBlock {

    public EmptyBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
    }

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

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
