package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) throws Exception {
        long curT;
        double delta;
        IOHandler io = new IOHandler();
        BoardState board = io.getInitInput();
        io.approveInit();
        //board.print();
        //BoardAnalyzer randomPlayerAnalyzer = new BoardAnalyzer(board);
        //MovesData movesData;
        //Random random = new Random();
        //delta = (double) (System.nanoTime() - curT) / 1000000;
        //BrickBlock nextPlayersMove;
        BrickBlock nextAIsMove;

        while (true) {
            //System.out.println("TURA GRACZA");
            //board.print();
             //Duo<Integer> move = io.getNextMove();
           // try {
            //nextPlayersMove = Duo.createBrickBlock(move.getLeft(), move.getRight(), board);
            /*movesData = randomPlayerAnalyzer.findAllMoves();
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL SUPER INTELIGENTNY PROGRAM    ************");
                break;
            }*/
            /*HashSet<BrickBlock> brickBlocksSet = BoardAnalyzer.mapValuesToSet(movesData.getMovesMap());
            ArrayList<BrickBlock> blocksArray = new ArrayList<>(brickBlocksSet);
            nextPlayersMove = blocksArray.get(random.nextInt(blocksArray.size()));
            //System.out.println("Gracz wybrał " + nextPlayersMove.toString());
            board.addBrick(nextPlayersMove);*/
            io.getNextMove(board);
            /*movesData = randomPlayerAnalyzer.findAllMoves();
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL GRACZ    ************");
                break;
            }*/
            //System.out.println("TURA PROGRAMU");
            //board.print();
            curT = System.nanoTime();
            nextAIsMove = MoveCalculator.nextMove(board);
            delta = (double) (System.nanoTime() - curT) / 1000000;
            //System.out.println("Program wybrał " + nextAIsMove.toString());
            board.addBrick(nextAIsMove);
            io.writeNextStep(nextAIsMove);
            if(delta >= 500)
                throw new Exception("Przekroczony czas na decyzję! ("+delta+" ms)");



            //System.out.println("Czas: " + delta);
            //board.print();



            //} catch (Exception e) {
            //   e.printStackTrace();
            //}

        }


    }
}
