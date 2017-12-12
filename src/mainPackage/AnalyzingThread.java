package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Inf on 2017-11-24.
 */
public class AnalyzingThread implements Runnable {

    private int nThreads;
    private int idNum;
    private BoardState board;
    private HashMap<Integer, Duo<BrickBlock>> moves;
    private int nStateChangeables;
    private int nMoves;

    public AnalyzingThread(int nThreads, int idNum, BoardState board){
        this.nThreads = nThreads;
        this.idNum = idNum;
        this.board = board;
        moves = new HashMap<>(board.size*board.size/nThreads+1);
    }

    private void addToMovesMap(int index, BrickBlock block){
        Duo move = moves.get(index);
        if(move == null)
            moves.put(index, new Duo<BrickBlock>(block));
        else
            try {
                move.put(block);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void searchMoves(int index){
        for(BlockRotation rotation : BlockRotation.values()) {
            BrickBlock result = BrickBlock.checkAndCreate(index, board, rotation);
            if (result != null) {
                addToMovesMap(index, result);
                nMoves++;
                if(result.isStateChanging())
                    nStateChangeables++;
            }
        }
    }

    @Override
    public void run() {
        for(int i=idNum; i<board.getCells().length; i+=nThreads)
            if(board.getCell(i) > 0)
                searchMoves(i);
    }

    public HashMap<Integer, Duo<BrickBlock>> getMoves() {
        return moves;
    }

    public int getNStateChangeables() {
        return nStateChangeables;
    }

    public int getNMoves() {
        return nMoves;
    }
}
