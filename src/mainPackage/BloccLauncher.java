package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;
import java.util.HashMap;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        BoardState board = BoardState.randomBoard(5, 5);
        //BoardState board = new BoardState(5, new int[]{0, 1, 2, 3, 4, 8, 9, 10, 11, 17, 18, 19, 22, 23, 24});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        analyzer.findAllMoves();
        IOHandler io = new IOHandler(board.size);
        long curT;
        double delta;
        board.print();
        while(analyzer.getStats().nMoves > 0) {
            System.out.println("TURA GRACZA");
            Duo<Integer> move = io.getNextMove();
            try {
                BrickBlock nextMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
                board.addBrick(nextMove, analyzer);
                System.out.println("Gracz dodał "+nextMove.toString());
                curT = System.nanoTime();
                board.print();
                nextMove = MoveCalculator.nextMove(analyzer);
                board.addBrick(nextMove, analyzer);
                delta = (double)(System.nanoTime() - curT)/1000000;
                System.out.println("TURA PROGRAMU");
                System.out.println("Program dodał "+nextMove.toString());
                System.out.println("Czas: " + delta);
                board.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
