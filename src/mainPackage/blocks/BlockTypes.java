package mainPackage.blocks;

import mainPackage.blocks.blocks1type.Block1Types;
import mainPackage.blocks.blocks2or1type.Block2or1Types;
import mainPackage.blocks.blocks2type.Block2Types;

/**
 * Created by Inf on 2017-11-21.
 */
public enum BlockTypes {
    Block2Types (Block2Types.class),
    Block2or1Types (Block2or1Types.class),
    Block1Types (Block1Types.class);

    private Class blockTypeClass;

    BlockTypes(Class blockTypesClass) {
        this.blockTypeClass = blockTypesClass;
    }

    public Class getBlockTypeClass() {
        return blockTypeClass;
    }
}
