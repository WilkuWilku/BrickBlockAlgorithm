package mainPackage;

import mainPackage.blocks.blocks1type.AbstractBlockType1;
import mainPackage.blocks.blocks1type.BrickBlock;
import mainPackage.blocks.blocks2or1type.AbstractBlockType2or1;
import mainPackage.blocks.blocks2type.AbstractBlockType2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Inf on 2017-12-12.
 */
public class BoardStatistics {
    private HashMap<Integer, Duo<BrickBlock>> movesMap;
    private ArrayList<AbstractBlockType1> blocksType1;
    private ArrayList<AbstractBlockType2> blocksType2;
    private ArrayList<AbstractBlockType2or1> blocksType2or1;
    public int nMoves = 0;              //licznik wszystkich możliwych ruchów
    public int[] n;                     //licznik komórek dla każdej wartości MoveReduction
    public int nMoveReductionBy1 = 0;                //licznik BrickBlocków, dla których MoveReduction == 1


    public BoardStatistics(){
        movesMap = new HashMap<>();
        n = new int[5];
        blocksType1 = new ArrayList<>();
        blocksType2 = new ArrayList<>();
        blocksType2or1 = new ArrayList<>();
    }

    public HashMap<Integer, Duo<BrickBlock>> getMovesMap() {
        return movesMap;
    }

    public void setMovesMap(HashMap<Integer, Duo<BrickBlock>> movesMap) {
        this.movesMap = movesMap;
    }

    public void addAllStats(BoardStatistics stats){
        movesMap.putAll(stats.getMovesMap());
        nMoves += stats.nMoves;
        nMoveReductionBy1 += stats.nMoveReductionBy1;
        for(int i=0; i<n.length; i++)
            n[i] += stats.n[i];
    }

    /* liczba pozostałych ruchów musi być nieparzysta, żeby wygrać */
    public MovesParityState checkMovesParity(){
        if(blocksType1.size() % 2 == 0)
            return MovesParityState.MUST_CHANGE;
        else
            return MovesParityState.MUST_STAY;
    }

    /* liczba pozostałych bloków typu 2/1 musi być nieparzysta, aby kontrolować grę */
    public Blocks2or1ParityState checkBlocks2or1Parity(){
        if(blocksType2or1.size() % 2 == 0)
            return Blocks2or1ParityState.MUST_CHANGE;
        else
            return Blocks2or1ParityState.MUST_STAY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Moves found: "+nMoves+"\n");
        sb.append("BrickBlocks found: "+ nMoveReductionBy1 +"\n");
        sb.append("Blocks found: "+(blocksType1.size()+blocksType2.size()+blocksType2or1.size())+"\n");
        for(int i=0; i<n.length; i++)
            sb.append("n"+i+": "+n[i]+"  ");
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
