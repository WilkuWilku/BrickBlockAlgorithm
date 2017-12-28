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
        //BoardState board = new BoardState(5, new int[]{1, 3, 4, 8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 21});
        board.print();

        curT = System.nanoTime();
        IOHandler io = new IOHandler(board.size);
        Tree movesTree;
        //MoveCalculator.nextMove(analyzer);

        //if(movesData.nMoves < 30) {
            movesTree = new Tree(board);
            movesTree.growTree(2);
            BrickBlock nextMove = movesTree.getMatchingMove();
        //}
        delta = (double)(System.nanoTime() - curT)/1000000;

        //System.out.println(movesData.toString());
        //System.out.println(blocksData.toString());
        System.out.println("NextMove: "+nextMove.toString());
        System.out.println("BlocksData: "+movesTree.getBlocksData().toString());
        System.out.println("MovesData: "+movesTree.getMovesData().toString());
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
