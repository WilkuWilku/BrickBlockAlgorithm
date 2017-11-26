package mainPackage;

import mainPackage.blocks.AbstractBlock;
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


    public ArrayList<AbstractBlock> getPossibilities() {
        return possibilities;
    }
}
