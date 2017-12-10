package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockRotation;

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
    private ArrayList<AbstractBlock> possibilities;
    private EnumWithClass[] types;

    public AnalyzingThread(int nThreads, int idNum, BoardState board, EnumWithClass[] types){
        this.nThreads = nThreads;
        this.idNum = idNum;
        this.board = board;
        this.types = types;
        possibilities = new ArrayList<>(board.size*board.size/nThreads+1);
    }

    /* sprawdza czy istnieje jakikolwiek blok w jakiejkolwiek rotacji dla każdej komórki.
     * jeśli istnieje - dodaje go do listy possibilities */
    private void researchPossibilities(EnumWithClass[] types, int index){
        for (int j = 0; j < types.length; j++) {
            try {
                Class blockClass = types[j].getBlockClass();
                Method method = blockClass.getMethod("check", int.class, BoardState.class, BlockRotation.class);
                for (BlockRotation rot : BlockRotation.values()) {
                    addBlock(index, board, rot, method);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /* dodaje blok jeśli istnieje dla danej komórki i rotacji */
    private void addBlock(int index, BoardState board, BlockRotation rotation, Method method){
        AbstractBlock result;
        try {
            result = (AbstractBlock) method.invoke(null, index, board, rotation);
                if (result != null)
                    possibilities.add(result);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ArrayList<Integer> listOfCells = board.getFreeCells();
        for(int i=idNum; i<board.getNFreeCells(); i+=nThreads)
            researchPossibilities(types, listOfCells.get(i));
    }

    public ArrayList<AbstractBlock> getPossibilities() {
        return possibilities;
    }
}
