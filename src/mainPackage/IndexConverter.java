package mainPackage;


/**
 * Created by Inf on 2017-11-18.
 */
/* klasa służąca do konwersji danych o położeniu komórki */
public final class IndexConverter {

    public static int xyToIndex(int x, int y, int size) {
        if(x<0 || x>=size || y<0 || y>=size)
            throw new IndexOutOfBoundsException("Błąd konwersji: komórka poza planszą!");
        return y*size+x;
    }

    public static Duo indexToXY(int index, int size){
        return new Duo(xOfIndex(index, size), yOfIndex(index, size));
    }

    public static int xOfIndex(int index, int size){
        return index % size;
    }

    public static int yOfIndex(int index, int size){
        return (int)(index/size);
    }
}
