package mainPackage.blocks;

import mainPackage.IndexConverter;

/**
 * Created by Inf on 2017-11-26.
 */
public class EmptyBlock extends AbstractBlock {

    public EmptyBlock(int referenceCellIndex, BlockRotation rotation){
        this.referenceCellIndex = referenceCellIndex;
        this.rotation = rotation;
    }

    public boolean isEmpty(){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNeighbours() {
        throw new UnsupportedOperationException();
    }
}
