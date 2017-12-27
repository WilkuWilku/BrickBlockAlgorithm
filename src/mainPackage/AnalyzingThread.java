package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashMap;

/**
 * Created by Inf on 2017-11-24.
 */
public class AnalyzingThread implements Runnable {

    private int nThreads;
    private int idNum;
    private BoardState board;
    private BoardStatistics stats;

    public AnalyzingThread(int nThreads, int idNum, BoardState board){
        this.nThreads = nThreads;
        this.idNum = idNum;
        this.board = board;
        stats = new BoardStatistics();
    }

    private void addToMovesMap(int index, BrickBlock block){
        Duo move = stats.getMovesMap().get(index);
        if(move == null)
            stats.getMovesMap().put(index, new Duo<>(block));
        else
            try {
                move.insert(block);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void searchMoves(int index){
        for(BlockRotation rotation : BlockRotation.values()) {
            BrickBlock result = BrickBlock.checkAndCreate(index, board, rotation);
            if (result != null) {
                addToMovesMap(index, result);
                stats.nMoves++;
            }
        }
    }

    @Override
    public void run() {
        for(int i=idNum; i<board.getCells().length; i+=nThreads)
            if(board.getCell(i) > 0)
                searchMoves(i);
    }

    public BoardStatistics getStats() {
        return stats;
    }
}
