package mainPackage;

import mainPackage.blocks.blocks1type.AbstractBlockType1;
import mainPackage.blocks.blocks1type.BrickBlock;
import mainPackage.blocks.blocks2or1type.AbstractBlockType2or1;
import mainPackage.blocks.blocks2type.AbstractBlockType2;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Inf on 2017-12-13.
 */
public final class MoveCalculator {

    private MoveCalculator(){
    }

    public static BrickBlock nextMove(BoardAnalyzer analyzer){
        //TODO poprawić funkcję obliczającą ruchy
        BoardStatistics stats = analyzer.findAllMoves();
        MovesParityState movesParity = stats.checkMovesParity();
        Blocks2or1ParityState blocks2or1Parity = stats.checkBlocks2or1Parity();

        /* plansza wyłącznie z nieokreślonymi blokami */
        BoardState boardWithoutBlocks = analyzer.getBoard().getBoardWithoutBlocks(stats);
        BoardAnalyzer boardWithoutBlocksAnalyzer = new BoardAnalyzer(boardWithoutBlocks);
        BoardStatistics statsWithoutBlocks = boardWithoutBlocksAnalyzer.findAllMoves();
        if(statsWithoutBlocks.nMoves > 0)
            return findBestMove(boardWithoutBlocksAnalyzer, statsWithoutBlocks);
        return null;
    }

    /* wyszukiwanie najlepszego ruchu sposród nieokreślonych bloków */
    private static BrickBlock findBestMove(BoardAnalyzer analyzer, BoardStatistics stats){
        HashSet<BrickBlock> movesWithoutBlocks = BoardAnalyzer.mapValuesToSet(stats.getMovesMap());
        for(AbstractBlockType1 move : movesWithoutBlocks){
            BoardState resultBoard = new BoardState(analyzer.getBoard());
            BoardAnalyzer resultBoardAnalyzer = new BoardAnalyzer(resultBoard);
            BoardStatistics resultStats = resultBoardAnalyzer.findAllMoves();

            //TODO drzewo ruchów

        }
        return null;
    }

    private static BrickBlock matchMove(BoardState board, BoardStatistics resultStats, BoardStatistics stats){
        MovesParityState movesParity = resultStats.checkMovesParity();
        Blocks2or1ParityState blocks2or1Parity = resultStats.checkBlocks2or1Parity();
        if(movesParity == MovesParityState.MUST_STAY){
            if(blocks2or1Parity == Blocks2or1ParityState.MUST_STAY){

            }
        }
        return null;
    }




}
