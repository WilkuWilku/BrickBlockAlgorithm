package mainPackage;

import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.*;


/**
 * Created by Inf on 2017-11-19.
 */
public class BoardAnalyzer {
    private BoardState board;


    public BoardAnalyzer(BoardState board) {
        this.board = board;
    }

    /* znajduje wszystkie możliwe ruchy (BrickBlocki) */
    public MovesData findAllMoves(){
        MovesData movesData = new MovesData();
        final int nThreads = 1;
        movesData.setMovesMap(new HashMap<>());
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
        movesData.nMoves = 0;
        for(int i=0; i<nThreads; i++)
            movesData.addAllStats(analyzingThreads[i].getMovesData());
        //createMRStats(movesData);
        return movesData;
    }

    /* wypisuje wszystkie BrickBlocki z mapy *//*
    public void printMoves(HashMap<Integer, Duo<BrickBlock>> moves){
        for (Duo<BrickBlock> move : moves.values()) {
            if(move.getLeft()!=null)
                System.out.println(move.getLeft().toString() + "; MovesLeft: "+(stats.nMoves-move.getLeft().getMovesReduction()) );
            if(move.getRight()!=null)
                System.out.println(move.getRight().toString() + "; MovesLeft: "+(stats.nMoves-move.getRight().getMovesReduction()));
        }
    }*/

    /* przerzuca ruchy z mapy do zbioru */
    public static HashSet<BrickBlock> mapValuesToSet(HashMap<Integer, Duo<BrickBlock>> map) {
        HashSet<BrickBlock> valuesSet = new HashSet<>();
        for (Duo<BrickBlock> value : map.values()) {
            valuesSet.add(value.getLeft());
            if (value.getRight() != null)
                valuesSet.add(value.getRight());
        }
        return valuesSet;
    }

    /*public BrickBlock getMove(int index, BlockRotation rotation){
        Duo<BrickBlock> move = stats.getMovesMap().get(index);
        if(move.getLeft().getRotation() == rotation)
            return move.getLeft();
        else if(move.getRight() != null) {
                if (move.getRight().getRotation() == rotation)
                    return move.getRight();
            }
        return null;
    }*/

    /* aktualizuje liczbę BrickBlokców bez sąsiadów (nMoveReductionBy1) i liczby komórek o danym MoveReduction (n[]) */
    private void createMRStats(MovesData movesData){
        movesData.nMoveReductionBy1 = 0;
        //Arrays.fill(movesData.n, 0);
        //createNStats(movesData);
        for(Duo<BrickBlock> moves : movesData.getMovesMap().values()) {
            if (moves.getLeft().getMovesReduction() == 1)
                movesData.nMoveReductionBy1++;
            if (moves.getRight() != null) {
                if(moves.getRight().getMovesReduction() == 1)
                    movesData.nMoveReductionBy1++;
            }
        }
    }

    /* aktualizuje statystyki n[] */
    /*private void createNStats(MovesData movesData){
        for(int i=0; i<board.size*board.size; i++){
            int moveReduction = board.getCell(i);
            switch (moveReduction) {
                case 1:
                    movesData.n[1]++;
                    break;
                case 2:
                    movesData.n[2]++;
                    break;
                case 3:
                    movesData.n[3]++;
                    break;
                case 4:
                    movesData.n[4]++;
                    break;
            }
        }
    }*/

    public BoardState getBoard() {
        return board;
    }
}
