package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Inf on 2017-11-19.
 */
public class BoardAnalyzer {
    private BoardState board;
    private BoardStatistics stats;

    public BoardAnalyzer(BoardState board) {
        this.board = board;
        stats = new BoardStatistics();
    }


    public HashMap<Integer, Duo<BrickBlock>> findAllMoves(){
        final int nThreads = 2;
        stats.setMovesMap(new HashMap<>());
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
        stats.nStateChangeables = 0;
        stats.nMoves = 0;
        for(int i=0; i<nThreads; i++)
            stats.addAllStats(analyzingThreads[i].getStats());
        createMRStats();
        return stats.getMovesMap();
    }


    public void printMoves(HashMap<Integer, Duo<BrickBlock>> moves){
        for (Duo<BrickBlock> move : moves.values()) {
            if(move.getLeft()!=null)
                System.out.println(move.getLeft().toString() + "; MovesLeft: "+(stats.nMoves-move.getLeft().getMovesReduction()) );
            if(move.getRight()!=null)
                System.out.println(move.getRight().toString() + "; MovesLeft: "+(stats.nMoves-move.getRight().getMovesReduction()));
        }

    }

    public BrickBlock getMove(int index, BlockRotation rotation){
        Duo<BrickBlock> move = stats.getMovesMap().get(index);
        if(move.getLeft().getRotation() == rotation)
            return move.getLeft();
        else if(move.getRight() != null) {
                if (move.getRight().getRotation() == rotation)
                    return move.getRight();
            }
        return null;
    }

    public void createMRStats(){
        for(Duo<BrickBlock> moves : getStats().getMovesMap().values()) {
            getMRStatsOfBrickBlock(moves.getLeft());
            if(moves.getRight() != null)
                getMRStatsOfBrickBlock(moves.getRight());
        }
    }

    private void getMRStatsOfCell(int index){
        int moveReduction = board.getCell(index);
        switch (moveReduction){
            case 1: stats.n[1]++; break;
            case 2: stats.n[2]++; break;
            case 3: stats.n[3]++; break;
            case 4: stats.n[4]++; break;
        }
    }

    private void getMRStatsOfBrickBlock(BrickBlock block){
        for(Integer i : block.getCells())
            getMRStatsOfCell(i);
    }

    public BoardStatistics getStats() {
        return stats;
    }
}
