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

public class MovesData {

    private HashMap<Integer, Duo<BrickBlock>> movesMap;     //mapa wszystkich ruchów
    public int nMoves = 0;              //licznik wszystkich możliwych ruchów
    public int[] n;                     //licznik komórek dla każdej wartości MoveReduction
    public int nMoveReductionBy1 = 0;                //licznik BrickBlocków, dla których MoveReduction == 1

    public MovesData(){
        movesMap = new HashMap<>();
        n = new int[5];
    }

    public HashMap<Integer, Duo<BrickBlock>> getMovesMap() {
        return movesMap;
    }

    public void setMovesMap(HashMap<Integer, Duo<BrickBlock>> movesMap) {
        this.movesMap = movesMap;
    }

    public void addAllStats(MovesData movesData){
        movesMap.putAll(movesData.getMovesMap());
        nMoves += movesData.nMoves;
        nMoveReductionBy1 += movesData.nMoveReductionBy1;
        for(int i=0; i<n.length; i++)
            n[i] += movesData.n[i];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Moves found: "+nMoves+"\n");
        sb.append("BrickBlocks found: "+ nMoveReductionBy1 +"\n");
        for(int i=0; i<n.length; i++)
            sb.append("n"+i+": "+n[i]+"  ");
        return sb.toString();
    }




}
