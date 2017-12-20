package mainPackage.blocks;

import mainPackage.BoardState;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-12-19.
 */
public interface Move {
    BrickBlock nextMove(BoardState board);
}
