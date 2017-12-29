package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import static mainPackage.BoardAnalyzer.mapValuesToSet;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        long curT;
        double delta;
        BoardState board = BoardState.randomBoard(10, 20);
        board.print();
        curT = System.nanoTime();
        IOHandler io = new IOHandler(board.size);
        BoardAnalyzer randomPlayerAnalyzer = new BoardAnalyzer(board);
        MovesData movesData;
        Random random = new Random();
        //delta = (double) (System.nanoTime() - curT) / 1000000;
        BrickBlock nextPlayersMove;
        BrickBlock nextAIsMove;

        while (true) {
            System.out.println("TURA GRACZA");
            board.print();
            // Duo<Integer> move = io.getNextMove();
            //try {
            //BrickBlock nextMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
            movesData = randomPlayerAnalyzer.findAllMoves();
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL SUPER INTELIGENTNY PROGRAM    ************");
                break;
            }
            HashSet<BrickBlock> brickBlocksSet = BoardAnalyzer.mapValuesToSet(movesData.getMovesMap());
            ArrayList<BrickBlock> blocksArray = new ArrayList<>(brickBlocksSet);
            nextPlayersMove = blocksArray.get(random.nextInt(blocksArray.size()));
            System.out.println("Gracz losowy wybrał " + nextPlayersMove.toString());
            board.addBrick(nextPlayersMove);
            System.out.println("po addBrick");
            board.print();

            //curT = System.nanoTime();

            movesData = randomPlayerAnalyzer.findAllMoves();
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL GRACZ LOSOWY    ************");
                break;
            }
            System.out.println("TURA PROGRAMU");
            board.print();
            nextAIsMove = MoveCalculator.nextMove(board);
            System.out.println("Program wybrał " + nextAIsMove.toString());
            board.addBrick(nextAIsMove);
            //delta = (double) (System.nanoTime() - curT) / 1000000;


            //System.out.println("Czas: " + delta);
            //board.print();

            //} catch (Exception e) {
            //   e.printStackTrace();
            //}

        }


    }
}
