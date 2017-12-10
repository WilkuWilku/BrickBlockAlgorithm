package mainPackage.blocks.blocks1type;

import mainPackage.EnumWithClass;

/**
 * Created by Inf on 2017-12-09.
 */
public enum Block1Types implements EnumWithClass {
    PinBlock(PinBlock.class),
    Straight3Block(Straight3Block.class),
    //BrickBlock(BrickBlock.class),
    TurnBlock(TurnBlock.class),
    PlusBlock(PlusBlock.class);

    private Class blockClass;

    Block1Types(Class blockClass) {
        this.blockClass = blockClass;
    }

    public Class getBlockClass() {
        return blockClass;
    }
}
