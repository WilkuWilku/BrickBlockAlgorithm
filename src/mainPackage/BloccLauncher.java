package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        long curT;
        double delta;
        BoardState board = BoardState.randomBoard(20, 285);
        board.print();

        curT = System.nanoTime();
        IOHandler io = new IOHandler(board.size);

        BrickBlock move = MoveCalculator.nextMove(board);

        delta = (double)(System.nanoTime() - curT)/1000000;

        System.out.println("NEXT MOVE: "+move.toString());
        System.out.println("Time: " + delta);

        /*while(analyzer.getMovesData().nMoves > 0) {
            System.out.println("TURA GRACZA");
            Duo<Integer> move = io.getNextMove();
            try {
                BrickBlock nextMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
                board.addBrick(nextMove);
                System.out.println("Gracz dodał "+nextMove.toString());
                curT = System.nanoTime();
                board.print();
                nextMove = MoveCalculator.nextMove(analyzer);
                if(analyzer.getMovesData().nMoves == 0)
                    System.out.println("\n\n\t\t<< KOMPUTER ZOSTAL POKONANY >>");
                board.addBrick(nextMove);
                delta = (double)(System.nanoTime() - curT)/1000000;
                System.out.println("TURA PROGRAMU");
                System.out.println("Program dodał "+nextMove.toString());
                System.out.println(analyzer.getMovesData().toString());
                System.out.println("Czas: " + delta);
                board.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
            analyzer.findAllMoves();
            if(analyzer.getMovesData().nMoves == 0)
                System.out.println("\n\n\t\t<< ZOSTALES POKONANY PRZEZ KOMPUTER >>");
        }
        */

    }
}
