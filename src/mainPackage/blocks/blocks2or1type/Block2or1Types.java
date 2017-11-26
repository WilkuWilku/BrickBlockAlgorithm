package mainPackage.blocks.blocks2or1type;

import mainPackage.EnumWithClass;

/**
 * Created by Inf on 2017-11-21.
 */
public enum Block2or1Types implements EnumWithClass{
    Straight4Block (Straight4Block.class),
    TBlock (TBlock.class),
    ThunderBlock (ThunderBlock.class),
    InvertedThunderBlock (InvertedThunderBlock.class),
    SlipperBlock (SlipperBlock.class),
    InvertedSlipperBlock (InvertedSlipperBlock.class),
    CrossBlock (CrossBlock.class),
    DuckBlock (DuckBlock.class),
    InvertedDuckBlock (InvertedDuckBlock.class);

    private Class blockClass;

    Block2or1Types(Class blockClass) {
        this.blockClass = blockClass;
    }

    public Class getBlockClass() {
        return blockClass;
    }
}
