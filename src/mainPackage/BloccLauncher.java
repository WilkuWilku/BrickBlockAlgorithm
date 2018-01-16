package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args){
        IOHandler io = new IOHandler();
        BoardState board = io.getInitInput();
        io.approveInit();
        BrickBlock nextAIsMove;
        long initTime=System.currentTimeMillis();
        boolean isPlaying = true;
        while (isPlaying) {
            isPlaying = io.getNextMove(board, initTime);
            if(!isPlaying)
                continue;
            nextAIsMove = MoveCalculator.nextMove(board, initTime);
            board.addBrick(nextAIsMove);
            io.writeNextStep(nextAIsMove);
        }
    }
}
