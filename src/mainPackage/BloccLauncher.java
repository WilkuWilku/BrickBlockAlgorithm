package mainPackage;

import mainPackage.blocks.BlockFinder;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        long curT;
        double delta;
        BoardState board = BoardState.randomBoard(20, 270);
        //BoardState board = new BoardState(5, new int[]{1, 3, 4, 8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 21});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        curT = System.nanoTime();
        MovesData movesData = analyzer.findAllMoves();
        BlocksData blocksData = new BlocksData();
        if(movesData.nMoves < 1000)
            blocksData = BlockFinder.searchForBlocks(board);

        IOHandler io = new IOHandler(board.size);



        //MoveCalculator.nextMove(analyzer);
        board.print();
        Tree movesTree = new Tree(board.getBoardWithoutBlocks(blocksData), movesData);
        movesTree.growTree(10);
        delta = (double)(System.nanoTime() - curT)/1000000;

        System.out.println(movesData.toString());
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
