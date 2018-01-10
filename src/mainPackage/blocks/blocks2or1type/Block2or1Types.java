package mainPackage.blocks.blocks2or1type;

import mainPackage.EnumWithClass;
import mainPackage.Summable;

/**
 * Created by Inf on 2017-11-21.
 */
public enum Block2or1Types implements EnumWithClass, Summable{
    Straight4Block (Straight4Block.class, 6),
    TBlock (TBlock.class, 8),
    ThunderBlock (ThunderBlock.class, 6),
    InvertedThunderBlock (InvertedThunderBlock.class, 6),
    SlipperBlock (SlipperBlock.class, 8),
    InvertedSlipperBlock (InvertedSlipperBlock.class, 8),
    CrossBlock (CrossBlock.class, 10),
    DuckBlock (DuckBlock.class, 8),
    InvertedDuckBlock (InvertedDuckBlock.class, 8),
    ShortLegBlock (ShortLegBlock.class, 6),
    InvertedShortLegBlock (InvertedShortLegBlock.class, 6);

    private Class blockClass;
    private int sum;

    Block2or1Types(Class blockClass, int sum) {
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
