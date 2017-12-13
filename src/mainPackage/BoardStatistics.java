package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashMap;

/**
 * Created by Inf on 2017-12-12.
 */
public class BoardStatistics {
    private HashMap<Integer, Duo<BrickBlock>> movesMap;
    public int nMoves = 0;              //licznik wszystkich możliwych ruchów
    public int[] n;                     //licznik komórek dla każdej wartości MoveReduction
    public int nStateChangeables = 0;
    public int nMR1 = 0;                //licznik BrickBlocków, dla których MoveReduction == 1

    public BoardStatistics(){
        movesMap = new HashMap<>();
        n = new int[5];
    }

    public BoardStatistics(HashMap<Integer, Duo<BrickBlock>> moves, int nMoves, int[] n, int nMR1){
        this.movesMap = moves;
        this.nMoves = nMoves;
        this.n = n;
        this.nMR1 = nMR1;
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
        return sb.toString();
    }
}
