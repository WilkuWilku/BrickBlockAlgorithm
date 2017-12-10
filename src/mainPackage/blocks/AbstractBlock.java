package mainPackage.blocks;

import mainPackage.BoardState;

/**
 * Created by Inf on 2017-11-18.
 */
public abstract class AbstractBlock {

    protected int referenceCellIndex;
    protected BlockRotation rotation;
    protected int shape[][];

    public int getReferenceCellIndex() {
        return referenceCellIndex;
    }
    public BlockRotation getRotation() {
        return rotation;
    }
    public int[][] getShape() {
        return shape;
    }
    public void markOnBoard(BoardState board){
        for(int i=0; i<shape.length; i++)
            board.setCell(referenceCellIndex+shape[i][1]*board.size+shape[i][0]);
    }
    public void unmarkOnBoard(BoardState board){
        for(int i=0; i<shape.length; i++)
            board.unsetCell(referenceCellIndex+shape[i][1]*board.size+shape[i][0]);
    }

    public abstract boolean hasNeighbours();


    @Override
    public String toString() {
        return getClass().getSimpleName() + "[refCellID: "+referenceCellIndex+", rot: "+rotation+"]";
    }
}
