package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        //BoardState board = BoardState.randomBoard(5, 12);
        BoardState board = new BoardState(4, new int[]{1, 2});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);

        long curT = System.nanoTime();
        HashMap<Integer, Duo<BrickBlock>> allMoves = analyzer.findAllMoves();

        double delta = (double)(System.nanoTime() - curT)/1000000;

        analyzer.printMoves(allMoves);
        board.print();
        System.out.println("Czas: "+delta+"ms, możliwych ruchów: "+analyzer.getNMoves());

        IOHandler io = new IOHandler(board.size);
        Duo<Integer> move = io.getNextMove();
        try {
            BrickBlock nextMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
            board.addBrick(nextMove, analyzer);
            allMoves = analyzer.findAllMoves();
            analyzer.printMoves(allMoves);
            board.print();
            System.out.println("Czas: "+delta+"ms, możliwych ruchów: "+analyzer.getNMoves());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
