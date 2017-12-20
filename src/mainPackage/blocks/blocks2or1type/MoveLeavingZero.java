package mainPackage.blocks.blocks2or1type;

import mainPackage.BoardState;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-12-19.
 */

/* Ruch blokujący drugi ruch przeciwnika w danym bloku 2/1 */

public interface MoveLeavingZero {
    BrickBlock leaveZeroMoves(BoardState board);
}
