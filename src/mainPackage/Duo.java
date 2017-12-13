package mainPackage;

import com.sun.deploy.panel.ExceptionListDialog;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Inf on 2017-11-18.
 */
/* klasa przechowująca dwie wartości typu T */
public class Duo<T> {
    private T left;
    private T right;

    public Duo(T left, T right){
        this.left = left;
        this.right = right;
    }

    public Duo(T left){
        this.left = left;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public void insert(T value) throws Exception {
        if(right == null)
            this.right = value;
        else
            throw new Exception("Brak wolnych miejsc dla wartości +" +value.toString());
    }

    @Override
    public String toString() {
        return "L = "+left.toString()+" / R = "+right.toString();
    }

    public static BrickBlock createBrickBlock(int index1, int index2, BoardState board) throws Exception{
        if(!(Math.abs(index1-index2)==1 || Math.abs(index1-index2)==board.size))
            throw new Exception("Błąd tworzenia BrickBlocka - nieprawidłowe współrzędne: ["+index1+"; "+index2+"]");
        else if(index1-index2==1)
            return new BrickBlock(index2, BlockRotation.R0, board);
        else if(index1-index2==-1)
            return new BrickBlock(index1, BlockRotation.R0, board);
        else if(index1-index2==board.size)
            return new BrickBlock(index2, BlockRotation.R90, board);
        else if(index1-index2==-(board.size))
            return new BrickBlock(index1, BlockRotation.R90, board);
        return null;
    }

}
