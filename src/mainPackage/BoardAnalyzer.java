package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.BlockTypes;
import mainPackage.blocks.blocks1type.BrickBlock;
import mainPackage.blocks.blocks2type.Blockible;
import mainPackage.blocks.blocks2type.Reducible;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Inf on 2017-11-19.
 */
public class BoardAnalyzer {
    private BoardState board;
    private ArrayList<BrickBlock> moves;
    private int nStateChangeable;

    public BoardAnalyzer(BoardState board) {
        this.board = board;
        //possibilities = new ArrayList<>(BoardState.size*BoardState.size);
    }


    public ArrayList<BrickBlock> findAllMoves(){
        final int nThreads = 2;
        moves = new ArrayList<>(board.size*board.size);
        AnalyzingThread[] analyzingThreads = new AnalyzingThread[nThreads];
        Thread[] threads = new Thread[nThreads];

        for(int i=0; i<nThreads; i++){
            analyzingThreads[i] = new AnalyzingThread(nThreads, i, board);
            threads[i] = new Thread(analyzingThreads[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nStateChangeable = 0;
        for(int i=0; i<nThreads; i++){
            moves.addAll(analyzingThreads[i].getPossibilities());
            nStateChangeable += analyzingThreads[i].getNStateChangeables();
        }
        return moves;
    }

    public int getNStateChangeable() {
        return nStateChangeable;
    }

    public ArrayList<BrickBlock> getMoves() {
        return moves;
    }
}
