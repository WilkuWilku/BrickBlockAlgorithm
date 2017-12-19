package mainPackage;

import mainPackage.blocks.BlockFinder;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        long curT;
        double delta;
        BoardState board = BoardState.randomBoard(100, 7200);
        //BoardState board = new BoardState(5, new int[]{1, 3, 4, 8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 21});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        curT = System.nanoTime();
        analyzer.findAllMoves();
        if(analyzer.getStats().nMoves < 1000)
            BlockFinder.searchForBlocks(analyzer.getStats(), board);
        delta = (double)(System.nanoTime() - curT)/1000000;
        IOHandler io = new IOHandler(board.size);

        board.print();
        System.out.println(analyzer.getStats().toString());
        System.out.println("Czas: " + delta);
        /*while(analyzer.getStats().nMoves > 0) {
            System.out.println("TURA GRACZA");
            Duo<Integer> move = io.getNextMove();
            try {
                BrickBlock nextMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
                board.addBrick(nextMove);
                System.out.println("Gracz dodał "+nextMove.toString());
                curT = System.nanoTime();
                board.print();
                nextMove = MoveCalculator.nextMove(analyzer);
                if(analyzer.getStats().nMoves == 0)
                    System.out.println("\n\n\t\t<< KOMPUTER ZOSTAL POKONANY >>");
                board.addBrick(nextMove);
                delta = (double)(System.nanoTime() - curT)/1000000;
                System.out.println("TURA PROGRAMU");
                System.out.println("Program dodał "+nextMove.toString());
                System.out.println(analyzer.getStats().toString());
                System.out.println("Czas: " + delta);
                board.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
            analyzer.findAllMoves();
            if(analyzer.getStats().nMoves == 0)
                System.out.println("\n\n\t\t<< ZOSTALES POKONANY PRZEZ KOMPUTER >>");
        }
        */

    }
}
