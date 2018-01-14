package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Inf on 2018-01-14.
 */
public class randomTest {
    public static void main(String[] args) throws TimeLimitException {
        long curT;
        double delta;
        IOHandler io = new IOHandler();
        BoardState board = io.getInitInput();
        //board = BoardState.randomBoard(8, 0);
        io.approveInit();
        BoardAnalyzer randomPlayerAnalyzer = new BoardAnalyzer(board);
        MovesData movesData;
        Random random = new Random();
        BrickBlock nextPlayersMove;
        BrickBlock nextAIsMove;

        boolean isFinished = false;
        while (!isFinished) {
            movesData = randomPlayerAnalyzer.findAllMoves(System.currentTimeMillis());
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL SUPER INTELIGENTNY PROGRAM    ************");
                isFinished = true;
                continue;
            }
            HashSet<BrickBlock> brickBlocksSet = BoardAnalyzer.mapValuesToSet(movesData.getMovesMap());
            ArrayList<BrickBlock> blocksArray = new ArrayList<>(brickBlocksSet);
            nextPlayersMove = blocksArray.get(random.nextInt(blocksArray.size()));
            board.addBrick(nextPlayersMove);
            //io.getNextMove(board);
            movesData = randomPlayerAnalyzer.findAllMoves(System.currentTimeMillis());
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL GRACZ    ************");
                isFinished = true;
                continue;
            }
            curT = System.nanoTime();
            nextAIsMove = MoveCalculator.nextMove(board, System.currentTimeMillis());
            delta = (double) (System.nanoTime() - curT) / 1000000;
            board.addBrick(nextAIsMove);
            io.writeNextStep(nextAIsMove);
            if (delta >= 500) {
                throw new TimeLimitException("Przekroczony czas na decyzję! (" + delta + " ms)");
            }
        }

    }
}

