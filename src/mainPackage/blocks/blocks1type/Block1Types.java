package mainPackage.blocks.blocks1type;

import mainPackage.EnumWithClass;
import mainPackage.Summable;

/**
 * Created by Inf on 2017-12-09.
 */
public enum Block1Types implements EnumWithClass, Summable {
    PinBlock(PinBlock.class, 6),
    Straight3Block(Straight3Block.class, 4),
    //BrickBlock(BrickBlock.class, 2),
    TurnBlock(TurnBlock.class, 4),
    PlusBlock(PlusBlock.class, 8);

    private Class blockClass;
    private int sum;

    Block1Types(Class blockClass, int sum) {
        this.blockClass = blockClass;
        this.sum = sum;
    }

    public Class getClassOfValue() {
        return blockClass;
    }

    public int getSum() {
        return sum;
    }
}
