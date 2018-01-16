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

/* klasa zawierająca dane o możliwych ruchach */

public class MovesData {

    private HashMap<Integer, Duo<BrickBlock>> movesMap;         //mapa wszystkich ruchów
    public int nMoves = 0;                                      //licznik wszystkich możliwych ruchów

    public MovesData(){
        movesMap = new HashMap<>();
    }

    public HashMap<Integer, Duo<BrickBlock>> getMovesMap() {
        return movesMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Moves found: "+nMoves+"\n");
        return sb.toString();
    }
}
