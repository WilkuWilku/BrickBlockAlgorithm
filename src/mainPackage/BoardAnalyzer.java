package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Inf on 2017-11-19.
 */
public class BoardAnalyzer {
    private BoardState board;
    private HashMap<Integer, Duo<BrickBlock>> moves;
    private int nStateChangeable;
    private int nMoves;

    public BoardAnalyzer(BoardState board) {
        this.board = board;
        //possibilities = new ArrayList<>(BoardState.size*BoardState.size);
    }


    public HashMap<Integer, Duo<BrickBlock>> findAllMoves(){
        final int nThreads = 2;
        moves = new HashMap<>();
        AnalyzingThread[] analyzingThreads = new AnalyzingThread[nThreads];
        Thread[] threads = new Thread[nThreads];

        for(int i=0; i<nThreads; i++){
            analyzingThreads[i] = new AnalyzingThread(nThreads, i, board);
            threads[i] = new Thread(analyzingThreads[i]);
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nStateChangeable = 0;
        nMoves = 0;
        for(int i=0; i<nThreads; i++){
            moves.putAll(analyzingThreads[i].getMoves());
            nStateChangeable += analyzingThreads[i].getNStateChangeables();
            nMoves += analyzingThreads[i].getNMoves();
        }
        return moves;
    }

    public int getNStateChangeable() {
        return nStateChangeable;
    }

    public HashMap<Integer, Duo<BrickBlock>> getMoves() {
        return moves;
    }

    public void printMoves(HashMap<Integer, Duo<BrickBlock>> moves){
        for (Duo<BrickBlock> move : moves.values()) {
            if(move.getLeft()!=null)
                System.out.println(move.getLeft().toString() + "; MovesLeft: "+(nMoves-move.getLeft().getMovesReduction()));
            if(move.getRight()!=null)
                System.out.println(move.getRight().toString() + "; MovesLeft: "+(nMoves-move.getRight().getMovesReduction()));
        }
    }

    public BrickBlock getMove(int index, BlockRotation rotation){
        Duo<BrickBlock> move = moves.get(index);
        if(move.getLeft().getRotation() == rotation)
            return move.getLeft();
        else if(move.getRight() != null) {
                if (move.getRight().getRotation() == rotation)
                    return move.getRight();
            }
        return null;
    }

    public int getNMoves() {
        return nMoves;
    }
}
