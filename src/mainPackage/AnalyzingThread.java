package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

/**
 * Created by Inf on 2017-11-24.
 */
public class AnalyzingThread implements Runnable {

    private static final long TIME_LIMIT = 460;
    private int nThreads;
    private int idNum;
    private BoardState board;
    private MovesData movesData;

    public AnalyzingThread(int nThreads, int idNum, BoardState board){
        this.nThreads = nThreads;
        this.idNum = idNum;
        this.board = board;
        movesData = new MovesData();
    }

   /* private void addToMovesMap(int index, BrickBlock block){
        Duo move = movesData.getMovesMap().get(index);
        if(move == null)
            movesData.getMovesMap().put(index, new Duo<>(block));
        else
            try {
                move.insert(block);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
*/
    private void searchMovesAtIndex(int index, long initTime){
        if(System.currentTimeMillis() - initTime > TIME_LIMIT )
            return;
        for(BlockRotation rotation : BlockRotation.values()) {
            BrickBlock result = BrickBlock.createIfPossible(index, board, rotation);
            if (result != null) {
                //addToMovesMap(index, result);
                movesData.nMoves++;
            }
        }
    }

    @Override
    public void run() {
        long initTime = System.currentTimeMillis();
        for(int i=idNum; i<board.getCells().length; i+=nThreads)
            if(board.getCell(i) > 0)
                searchMovesAtIndex(i, initTime);
    }

    public MovesData getMovesData() {
        return movesData;
    }
}
