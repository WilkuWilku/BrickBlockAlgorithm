package mainPackage.blocks;

import mainPackage.*;
import mainPackage.blocks.blocks1type.AbstractBlockType1;
import mainPackage.blocks.blocks1type.BrickBlock;
import mainPackage.blocks.blocks2or1type.AbstractBlockType2or1;
import mainPackage.blocks.blocks2type.AbstractBlockType2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Inf on 2017-11-18.
 */
public class BlockFinder<T extends AbstractBlock>{
    private Class<T> tClass;

    public BlockFinder(Class<T> tClass){
        this.tClass = tClass;
    }

    /* coords - tablica dwuelementowych tablic (NIE int[x][y]!)
     * x = coords[a][0]
     * y = coords[a][1]
     * */
    private T findWithRotation(int index, int[][] coords, Summable type, BoardState board, BlockRotation rotation ) {
        if(coords == null)
            return null;
        int targetSum = type.getSum();
        int currentSum = 0;
        int cellReduction = 0;
        int x = IndexConverter.xOfIndex(index, board.size);
        int y = IndexConverter.yOfIndex(index, board.size);
            for(int i=0; i<coords.length; i++){
                if((cellReduction = board.getCell(x+coords[i][0], y+coords[i][1])) == 0)
                    return null;
                else currentSum += cellReduction;
                }

            /* jeśli sumy się nie zgadzają, to blok ma sąsiada - wówczas nie można jednoznacznie określić typu */
            if(currentSum != targetSum)
                return null;

            /* blok nie ma sąsiadów, jest więc typu 1, 2, albo 2/1 */
            /* stwarza nowy obiekt szukanego bloku */
            try {
                if(tClass == BrickBlock.class){
                    Constructor<T> constructor = tClass.getConstructor(int.class, BlockRotation.class, BoardState.class);
                    return (constructor.newInstance(index, rotation, board));
                }
                else {
                    Constructor<T> constructor = tClass.getConstructor(int.class, BlockRotation.class);
                    return (constructor.newInstance(index, rotation));
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        return null;
    }

    /* wywołuje metodę findWithRotation() z int[][] shape odpowiednim dla danej rotacji */
    public T find(int index, int[][] shapeR0, int[][] shapeR90, int[][] shapeR180, int[][] shapeR270, Summable type, BoardState board, BlockRotation rotation){
        switch (rotation){
            case R0: return findWithRotation(index, shapeR0, type, board, rotation);
            case R90: return findWithRotation(index, shapeR90, type,board, rotation);
            case R180: return findWithRotation(index, shapeR180, type,board, rotation);
            case R270: return findWithRotation(index, shapeR270, type,board, rotation);
            default: return null;
        }
    }

    public static BlocksData searchForBlocks(BoardState board){
        BlocksData blocksData = new BlocksData();
        for(int i=0; i<board.size*board.size; i++){
            if(board.getCell(i) > 0)
                searchForBlocksAtIndex(i, blocksData, board);
        }
        return blocksData;
    }

    public static BlocksData searchForBlocksInArea(BoardState board, int referenceCellID, int width, int height) {
        BlocksData blocksData = new BlocksData();
        int refX = IndexConverter.xOfIndex(referenceCellID, board.size);
        int refY = IndexConverter.yOfIndex(referenceCellID, board.size);
        for(int x=(refX-width/2 < 0) ? 0 : refX-width/2; x < refX+width && x < board.size; x++){
            for(int y=(refY-height/2 < 0) ? 0 : refY-height/2; y < refY+height && y < board.size; y++){
                searchForBlocksAtIndex(IndexConverter.xyToIndex(x, y, board.size), blocksData, board);
            }
        }
        return blocksData;
    }


    private static void searchForBlocksAtIndex(int index, BlocksData blocksData, BoardState board){
        /* Enum z typami bloków */
        Class typesRootClass = BlockTypes.class;

        /* Tablica wartości z klasami typów bloków */
        EnumWithClass[] types = (EnumWithClass[]) typesRootClass.getEnumConstants();

        for(int i=0; i<types.length; i++) {

            /* Enum z klasami bloków danego typu */
            Class typeClass = types[i].getClassOfValue();

            /* Tablica wartości z klasami bloków danego typu */
            EnumWithClass[] blockTypes = (EnumWithClass[])  typeClass.getEnumConstants();

            for (int j = 0; j < blockTypes.length; j++) {
                try {

                    /* Klasa bloku danego typu */
                    Class blockClass = blockTypes[j].getClassOfValue();

                    Method method = blockClass.getMethod("check", int.class, BoardState.class, BlockRotation.class);
                    for (BlockRotation rot : BlockRotation.values()) {
                        addBlockToList(index, board, rot, method, blocksData);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* dodaje blok jeśli istnieje dla danej komórki i rotacji */
    private static void addBlockToList(int index, BoardState board, BlockRotation rotation, Method method, BlocksData blocksData){
        AbstractBlock result;
        try {
            result = (AbstractBlock) method.invoke(null, index, board, rotation);
            if (result != null){
                if(result instanceof AbstractBlockType1)
                    blocksData.getBlocksType1().add((AbstractBlockType1) result);
                else if(result instanceof AbstractBlockType2)
                    blocksData.getBlocksType2().add((AbstractBlockType2) result);
                else if(result instanceof AbstractBlockType2or1)
                    blocksData.getBlocksType2or1().add((AbstractBlockType2or1) result);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }



}
