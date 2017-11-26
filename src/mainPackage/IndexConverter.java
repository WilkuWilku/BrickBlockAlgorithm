package mainPackage;

import mainPackage.Dual;

/**
 * Created by Inf on 2017-11-18.
 */
public final class IndexConverter {

    public static int xyToIndex(int x, int y, int size){
        return y*size+x;
    }

    public static Dual indexToXY(int index, int size){
        return new Dual((int)(index/size), index % size);
    }

    public static int xOfIndex(int index, int size){
        return (int)(index/size);
    }

    public static int yOfIndex(int index, int size){
        return index % size;
    }
}
