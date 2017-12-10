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
    private final int AREA_WIDTH = 4;
    private BoardState board;
    private ArrayList<AbstractBlock> possibilities;
    private ArrayList<ArrayList<AbstractBlock>> coverings;
    public static final int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53};

    public BoardAnalyzer(BoardState board) {
        this.board = board;
        possibilities = new ArrayList<>(BoardState.size*BoardState.size);
        coverings = new ArrayList<>();
    }

    /*
        areaIndex - indeks obszaru poszukiwań (0-15)
        boardIndex - indeks komórki na planszy
        areaState[areaIndex] - stan komórki w obszarze poszukiwań
        primes - kolejne liczby pierwsze odpowiadające komórkom obszaru poszukiwań
        areaStateId - unikalne ID stanu wszystkich komórek obszaru poszukiwań (iloczyn primes zajętych komórek)
     */
    private BigDecimal generateAreaStateId(int startingIndex){
        BigDecimal areaStateId = new BigDecimal(1);
        boolean[] areaState = new boolean[AREA_WIDTH*AREA_WIDTH];
        int boardIndex, areaIndex;
        for(int y=0; y<AREA_WIDTH; y++){
            for(int x=0; x<AREA_WIDTH; x++){
                boardIndex = y*board.size+startingIndex+x;
                areaIndex = y*AREA_WIDTH+x;
                areaState[areaIndex] = board.getCell(boardIndex);
                if(!areaState[areaIndex])
                    areaStateId = areaStateId.multiply(new BigDecimal(primes[areaIndex]));
            }
        }
        return areaStateId;
    }

    public ArrayList findPossibilitiesWithDB(){
        for(int x=0; x<board.size-AREA_WIDTH; x++){
            for(int y=0; y<board.size-AREA_WIDTH; y++){
                findPossibilitiesAtIndexWithDB(x+board.size*y);
            }
        }
        return possibilities;
    }

    private void findPossibilitiesAtIndexWithDB(int startingIndex){
        ResultSet resultSet;
        DatabaseHandler db = DatabaseHandler.getInstance();
        resultSet = db.getFilteredResults(generateAreaStateId(startingIndex));
        saveResultSetToArrayList(startingIndex, resultSet);
    }

    private void saveResultSetToArrayList(int startingIndex, ResultSet resultSet){
        try {
            Class tClass;
            BlockRotation rotation;
            while(resultSet.next()){
                try {
                    tClass = Class.forName("mainPackage.blocks.blocks2or1type." + resultSet.getString("block"));
                }
                catch (ClassNotFoundException e){
                    /* nie ten package */
                    tClass = Class.forName("mainPackage.blocks.blocks2type." + resultSet.getString("block"));
                }
                rotation = BlockRotation.valueOf(resultSet.getString("rotation"));
                Constructor constructor = tClass.getConstructor(int.class, BlockRotation.class);
                AbstractBlock block = (AbstractBlock) constructor.newInstance(startingIndex, rotation);
                possibilities.add(block);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
                for (BlockTypes blockTypes : BlockTypes.values()) {
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

    public ArrayList<ArrayList<AbstractBlock>> getCoverings() {
        return coverings;
    }

    public ArrayList<AbstractBlock> getPossibilities() {
        return possibilities;
    }
}
