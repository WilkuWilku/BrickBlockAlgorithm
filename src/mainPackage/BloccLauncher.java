package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;
import java.util.HashMap;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        BoardState board = BoardState.randomBoard(200, 30000);
        //BoardState board = new BoardState(5, new int[]{0, 1, 2, 3, 4, 8, 9, 10, 11, 17, 18, 19, 22, 23, 24});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        IOHandler io = new IOHandler(board.size);
        long curT = System.nanoTime();
        HashMap<Integer, Duo<BrickBlock>> allMoves = analyzer.findAllMoves();

        double delta = (double)(System.nanoTime() - curT)/1000000;

        analyzer.printMoves(allMoves);
        //board.print();
        System.out.println(analyzer.getStats().toString());
        System.out.println("Czas: "+delta+"ms, możliwych ruchów: "+analyzer.getStats().nMoves);
        while(analyzer.getStats().nMoves > 1) {

            Duo<Integer> move = io.getNextMove();
            try {
                BrickBlock nextMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
                board.addBrick(nextMove, analyzer);
                System.out.println("*** dodano "+nextMove.toString());
                curT = System.nanoTime();
                allMoves = analyzer.findAllMoves();
                delta = (double)(System.nanoTime() - curT)/1000000;
                analyzer.printMoves(allMoves);
                //board.print();
                System.out.println(analyzer.getStats().toString());
                System.out.println("Czas: " + delta + "ms, możliwych ruchów: " + analyzer.getStats().nMoves);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
