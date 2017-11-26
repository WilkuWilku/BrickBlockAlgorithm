package mainPackage.blocks.blocks2type;

import mainPackage.EnumWithClass;

/**
 * Created by Inf on 2017-11-19.
 */

/* klasy bloków w typie wyliczeniowym dla łatwiejszego wyszukiwania możliwych bloków na planszy */
public enum Block2Types implements EnumWithClass{
    BootBlock (BootBlock.class),
    LBlock (LBlock.class),
    LegBlock (LegBlock.class),
    SBlock (SBlock.class),
    SquareBlock (SquareBlock.class),
    StairsBlock (StairsBlock.class),
    Straight5Block (Straight5Block.class),
    UBlock (UBlock.class),
    WormBlock (WormBlock.class),
    InvertedBootBlock (InvertedBootBlock.class),
    InvertedSBlock (InvertedSBlock.class),
    InvertedWormBlock (InvertedWormBlock.class),
    InvertedLegBlock (InvertedLegBlock.class);

    private Class blockClass;

    Block2Types(Class blockClass) {
        this.blockClass = blockClass;
    }

    public Class getBlockClass() {
        return blockClass;
    }
}
