package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.Blocks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Created by Inf on 2017-11-19.
 */
public class BoardAnalyzer {

    private BoardState board;
    private ArrayList<AbstractBlock> possibilities;
    private ArrayList<ArrayList<AbstractBlock>> coverings;

    public BoardAnalyzer(BoardState board) {
        this.board = board;
        possibilities = new ArrayList<>(BoardState.size*BoardState.size);
        coverings = new ArrayList<>();
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

    public void findBoardCoverings(BoardState board){
        recursiveCovering(0, board, new ArrayList());
    }



    private void recursiveCovering(int startingIndex, BoardState board, ArrayList list) {

        for (int i = startingIndex; i < board.size * board.size; i++) {
            if(!board.getCell(i)) {
                for (Blocks blockTypes : Blocks.values()) {
                    Class typeClass = blockTypes.getBlockTypeClass();
                    EnumWithClass[] types = (EnumWithClass[]) typeClass.getEnumConstants();
                    for (int j = 0; j < types.length; j++) {
                        try {
                            Class blockClass = types[j].getBlockClass();
                            Method method = blockClass.getMethod("check", int.class, BoardState.class, BlockRotation.class);
                            for (BlockRotation rot : BlockRotation.values()) {
                                AbstractBlock result;
                                result = (AbstractBlock) method.invoke(null, i, board, rot);
                                if (result != null) {
                                    list.add(result);
                                    result.markOnBoard(board);
                                    recursiveCovering(i+1, board, list);
                                    result.unmarkOnBoard(board);
                                    list.remove(result);

                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        coverings.add(new ArrayList<AbstractBlock>(list));



    }

    public ArrayList<ArrayList<AbstractBlock>> getCoverings() {
        return coverings;
    }

    public ArrayList<AbstractBlock> getPossibilities() {
        return possibilities;
    }
}
