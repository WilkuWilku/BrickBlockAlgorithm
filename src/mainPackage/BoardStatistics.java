package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockTypes;
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
    public int nStateChangeables = 0;
    public int nMR1 = 0;                //licznik BrickBlocków, dla których MoveReduction == 1


    public BoardStatistics(){
        movesMap = new HashMap<>();
        n = new int[5];
        blocksType1 = new ArrayList<>();
        blocksType2 = new ArrayList<>();
        blocksType2or1 = new ArrayList<>();
    }

    public BoardStatistics(HashMap<Integer, Duo<BrickBlock>> moves, int nMoves, int[] n, int nMR1){
        this.movesMap = moves;
        this.nMoves = nMoves;
        this.n = n;
        this.nMR1 = nMR1;
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
        nStateChangeables += stats.nStateChangeables;
        nMR1 += stats.nMR1;
        for(int i=0; i<n.length; i++)
            n[i] += stats.n[i];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("nMoves: "+nMoves+"\n");
        sb.append("nStateChangeables: "+nStateChangeables+"\n");
        sb.append("nMR1: "+nMR1+"\n");
        for(int i=0; i<n.length; i++)
            sb.append("n"+i+": "+n[i]+"  ");
        sb.append("\n");
        sb.append("\tTYPE1:\n");
        sb.append(blocksType1.toString()+"\n");
        sb.append("\tTYPE2:\n");
        sb.append(blocksType2.toString()+"\n");
        sb.append("\tTYPE2/1:\n");
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
