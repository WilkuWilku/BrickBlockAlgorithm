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

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public void insert(T value) {
        if(right == null)
            this.right = value;
    }

    @Override
    public String toString() {
        return "L = "+left.toString()+" / R = "+right.toString();
    }

}
