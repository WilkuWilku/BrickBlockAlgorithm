package mainPackage;

/**
 * Created by Inf on 2017-11-18.
 */
/* klasa przechowująca dwie wartości typu T */
public class Dual<T> {
    private T left;
    private T right;

    public Dual(T left, T right){
        this.left = left;
        this.right = right;
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
}
