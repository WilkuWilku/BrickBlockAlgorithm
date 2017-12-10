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
    private ArrayList<AbstractBlock> possibilities;

    public BoardAnalyzer(BoardState board) {
        this.board = board;
        possibilities = new ArrayList<>(BoardState.size*BoardState.size);
    }

    @Deprecated
    public void findAllPossibilities(Class typeClass){
        final int threadNo = 2;
        EnumWithClass[] types = (EnumWithClass[]) typeClass.getEnumConstants();
        AnalyzingThread[] analyzingThreads = new AnalyzingThread[threadNo];
        Thread[] threads = new Thread[threadNo];

        for(int i=0; i<threadNo; i++){
            analyzingThreads[i] = new AnalyzingThread(threadNo, i, board, types);
            threads[i] = new Thread(analyzingThreads[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            for(int i=0; i<threadNo; i++){
            possibilities.addAll(analyzingThreads[i].getPossibilities());
        }
    }

    public void findAllMoves(){
        final int nThreads = 2;
        AnalyzingThread[] analyzingThreads = new AnalyzingThread[nThreads];
        Thread[] threads = new Thread[nThreads];

        for(int i=0; i<nThreads; i++){
            analyzingThreads[i] = new AnalyzingThread(nThreads, i, board, null);
            threads[i] = new Thread(analyzingThreads[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<nThreads; i++){
            possibilities.addAll(analyzingThreads[i].getPossibilities());
        }
    }



    public int getNBlockibles(){
        int nBlockibles =0;
        for(AbstractBlock block : possibilities){
            if(block instanceof Blockible)
                nBlockibles++;
        }
        return nBlockibles;
    }


    public int getNReducibles(){
        int nReducibles =0;
        for(AbstractBlock block : possibilities){
            if(block instanceof Reducible)
                nReducibles++;
        }
        return nReducibles;
    }

    public int getNBrickBlocks(){
        int n=0;
        for(AbstractBlock block : possibilities){
            if(block instanceof BrickBlock)
                n++;
        }
        return n;
    }

    public ArrayList<AbstractBlock> getPossibilities() {
        return possibilities;
    }
}
