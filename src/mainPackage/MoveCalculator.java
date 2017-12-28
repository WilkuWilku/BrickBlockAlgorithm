package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.blocks1type.AbstractBlockType1;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashSet;

/**
 * Created by Inf on 2017-12-13.
 */
public final class MoveCalculator {

    private MoveCalculator(){
    }

    public static BrickBlock nextMove(BoardAnalyzer analyzer){
        //TODO poprawić funkcję obliczającą ruchy
        MovesData movesData = analyzer.findAllMoves();
        BlocksData blocksData = BlockFinder.searchForBlocks(analyzer.getBoard());
        LeadingState movesParity = blocksData.checkMovesParity();
        ControlState blocks2or1Parity = blocksData.checkControlState();

        /* plansza wyłącznie z nieokreślonymi blokami */
        BoardState boardWithoutBlocks = analyzer.getBoard().getBoardWithoutBlocks(blocksData);
        BoardAnalyzer boardWithoutBlocksAnalyzer = new BoardAnalyzer(boardWithoutBlocks);
        MovesData statsWithoutBlocks = boardWithoutBlocksAnalyzer.findAllMoves();
        if(statsWithoutBlocks.nMoves > 0)
            return findBestMove(boardWithoutBlocksAnalyzer, statsWithoutBlocks);
        return null;
    }

    /* wyszukiwanie najlepszego ruchu sposród nieokreślonych bloków */
    private static BrickBlock findBestMove(BoardAnalyzer analyzer, MovesData stats){
        HashSet<BrickBlock> movesWithoutBlocks = BoardAnalyzer.mapValuesToSet(stats.getMovesMap());
        for(AbstractBlockType1 move : movesWithoutBlocks){
            BoardState resultBoard = new BoardState(analyzer.getBoard());
            BoardAnalyzer resultBoardAnalyzer = new BoardAnalyzer(resultBoard);
            MovesData resultStats = resultBoardAnalyzer.findAllMoves();

            //TODO drzewo ruchów

        }
        return null;
    }

    private static BrickBlock matchMove(BoardState board, BlocksData resultBlocksData, MovesData stats){
        LeadingState movesParity = resultBlocksData.checkMovesParity();
        ControlState blocks2or1Parity = resultBlocksData.checkControlState();
        if(movesParity == LeadingState.MUST_STAY){
            if(blocks2or1Parity == ControlState.ODD){

            }
        }
        return null;
    }




}
