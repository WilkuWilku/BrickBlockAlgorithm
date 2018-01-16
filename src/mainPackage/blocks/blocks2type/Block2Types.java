package mainPackage.blocks.blocks2type;

import mainPackage.EnumWithClass;
import mainPackage.Summable;

/**
 * Created by Inf on 2017-11-19.
 */

/* klasy bloków w typie wyliczeniowym dla łatwiejszego wyszukiwania możliwych bloków na planszy */
public enum Block2Types implements EnumWithClass, Summable{
    BootBlock (BootBlock.class, 10),
    LBlock (LBlock.class, 8),
    LegBlock (LegBlock.class, 8),
    SBlock (SBlock.class, 8),
    SquareBlock (SquareBlock.class, 8),
    StairsBlock (StairsBlock.class, 8),
    Straight5Block (Straight5Block.class, 8),
    UBlock (UBlock.class, 8),
    WormBlock (WormBlock.class, 8),
    InvertedBootBlock (InvertedBootBlock.class, 10),
    InvertedSBlock (InvertedSBlock.class, 8),
    InvertedWormBlock (InvertedWormBlock.class, 8),
    InvertedLegBlock (InvertedLegBlock.class, 8),
    LongThunderBlock (LongThunderBlock.class, 12),
    InvertedLongThunderBlock (InvertedLongThunderBlock.class, 12);


    private Class blockClass;               // klasa danego bloku
    private int sum;                        // suma kontrolna, pozwala uniknąć powtórzeń bloków

    Block2Types(Class blockClass, int sum) {
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
