package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args){
        //long curT;
        //double delta;
        IOHandler io = new IOHandler();
        BoardState board = io.getInitInput();
        //board = BoardState.randomBoard(25, 0);
        io.approveInit();
        //BoardAnalyzer randomPlayerAnalyzer = new BoardAnalyzer(board);
        //MovesData movesData;
        //Random random = new Random();
        //BrickBlock nextPlayersMove;
        BrickBlock nextAIsMove;
        long initTime=System.currentTimeMillis();
        boolean isPlaying = true;
        while (isPlaying) {
            //movesData = randomPlayerAnalyzer.findAllMoves(System.currentTimeMillis());
            /*if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL SUPER INTELIGENTNY PROGRAM    ************");
                isPlaying = true;
                continue;
            }*/

            /*HashSet<BrickBlock> brickBlocksSet = BoardAnalyzer.mapValuesToSet(movesData.getMovesMap());
            ArrayList<BrickBlock> blocksArray = new ArrayList<>(brickBlocksSet);
            nextPlayersMove = blocksArray.get(random.nextInt(blocksArray.size()));
            board.addBrick(nextPlayersMove);*/

            isPlaying = io.getNextMove(board, initTime);
            if(!isPlaying)
                continue;
            /*movesData = randomPlayerAnalyzer.findAllMoves(System.currentTimeMillis());
            if (movesData.nMoves == 0) {
                System.out.println("*********     WYGRAL GRACZ    ************");
                isPlaying = true;
                continue;
            }*/
            //curT = System.nanoTime();
            nextAIsMove = MoveCalculator.nextMove(board, initTime);
            //delta = (double) (System.nanoTime() - curT) / 1000000;
            board.addBrick(nextAIsMove);
            io.writeNextStep(nextAIsMove);
            //if (delta >= 500) {
            //    throw new TimeLimitException("Przekroczony czas na decyzję! ("+delta+" ms)");
            //}
        }

    }
}
