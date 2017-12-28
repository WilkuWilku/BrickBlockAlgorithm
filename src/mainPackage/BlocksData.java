package mainPackage;

import mainPackage.blocks.blocks1type.AbstractBlockType1;
import mainPackage.blocks.blocks2or1type.AbstractBlockType2or1;
import mainPackage.blocks.blocks2type.AbstractBlockType2;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-12-27.
 */
public class BlocksData {
    private ArrayList<AbstractBlockType1> blocksType1;
    private ArrayList<AbstractBlockType2> blocksType2;
    private ArrayList<AbstractBlockType2or1> blocksType2or1;

    public BlocksData(){
        blocksType1 = new ArrayList<>();
        blocksType2 = new ArrayList<>();
        blocksType2or1 = new ArrayList<>();
    }

    /* liczba pozostałych ruchów musi być nieparzysta, żeby wygrać */
    public LeadingState checkMovesParity(){
        if(blocksType1.size() % 2 == 0)
            return LeadingState.MUST_CHANGE;
        else
            return LeadingState.MUST_STAY;
    }

    /* liczba pozostałych bloków typu 2/1 musi być nieparzysta, aby kontrolować grę */
    public ControlState checkControlState(){
        if(blocksType2or1.size() % 2 == 0)
            return ControlState.EVEN;
        else
            return ControlState.ODD;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Blocks found: "+(blocksType1.size()+blocksType2.size()+blocksType2or1.size())+"\n");

        sb.append("\n");
        sb.append("\tBLOCKS TYPE1 ["+blocksType1.size()+"]:\n");
        sb.append(blocksType1.toString()+"\n");
        sb.append("\tBLOCKS TYPE2 ["+blocksType2.size()+"]:\n");
        sb.append(blocksType2.toString()+"\n");
        sb.append("\tBLOCKS TYPE2/1 ["+blocksType2or1.size()+"]:\n");
        sb.append(blocksType2or1.toString()+"\n");
        return sb.toString();
    }

    public ArrayList<AbstractBlockType1> getBlocksType1() {
        return blocksType1;
    }

    public ArrayList<AbstractBlockType2> getBlocksType2() {
        return blocksType2;
    }

    public ArrayList<AbstractBlockType2or1> getBlocksType2or1() {
        return blocksType2or1;
    }

}
