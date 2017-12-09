package mainPackage.blocks;

import mainPackage.BoardState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    private T findWithRotation(int index, int[][] coords, int width, int height, BoardState board, BlockRotation rotation) {
        if(coords == null)
            return null;
        //if(index+height*board.size+width < board.size * board.size){
            for(int i=0; i<coords.length; i++){
                    if(board.getCell(index+coords[i][1]*board.size+coords[i][0]))
                        return null;
                }

            /* wszystkie pola bloku są false */
            /* stwarza nowy obiekt szukanego bloku */
            try {
                Constructor<T> constructor = tClass.getConstructor(int.class, BlockRotation.class);
                return (constructor.newInstance(index, rotation));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        //}
        return null;
    }

    /* wywołuje metodę findWithRotation() z int[][] shape odpowiednim dla danej rotacji */
    public T find(int index, int[][] shapeR0, int[][] shapeR90, int[][] shapeR180, int[][] shapeR270, int initWidth, int initHeight, BoardState board, BlockRotation rotation){
        switch (rotation){
            case R0: return findWithRotation(index, shapeR0, initWidth, initHeight, board, rotation);
            case R90: return findWithRotation(index, shapeR90, initHeight, initWidth,board, rotation);
            case R180: return findWithRotation(index, shapeR180, initWidth, initHeight,board, rotation);
            case R270: return findWithRotation(index, shapeR270, initHeight, initWidth,board, rotation);
            default: return null;
        }
    }


}
