package mainPackage.blocks.blocks2or1type;

import mainPackage.BoardState;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-12-19.
 */

/*Ruch pozostawiający następny ruch przeciwnikowi w danym bloku */

public interface MoveLeavingOne {
    BrickBlock leaveOneMove(BoardState board);
}
