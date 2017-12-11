package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-24.
 */
public class AnalyzingThread implements Runnable {

    private int nThreads;
    private int idNum;
    private BoardState board;
    private ArrayList<BrickBlock> possibilities;
    private int nStateChangeables;

    public AnalyzingThread(int nThreads, int idNum, BoardState board){
        this.nThreads = nThreads;
        this.idNum = idNum;
        this.board = board;
        possibilities = new ArrayList<>(board.size*board.size/nThreads+1);
    }


    private void searchMoves(int index){
        for(BlockRotation rotation : BlockRotation.values()) {
            BrickBlock result = BrickBlock.checkAndCreate(index, board, rotation);
            if (result != null) {
                possibilities.add(result);
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

    public ArrayList<BrickBlock> getPossibilities() {
        return possibilities;
    }

    public int getNStateChangeables() {
        return nStateChangeables;
    }
}
