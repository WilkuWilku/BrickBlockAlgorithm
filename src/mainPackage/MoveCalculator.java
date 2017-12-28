package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.Blocks;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashSet;

/**
 * Created by Inf on 2017-12-13.
 */
public final class MoveCalculator {

    private static final int TREE_MOVES_LIMIT = 15;

    private MoveCalculator(){
    }

    public static BrickBlock nextMove(BoardState board){
        //TODO testy testy testy
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        MovesData movesData = analyzer.findAllMoves();
        BlocksData blocksData = BlockFinder.searchForBlocks(analyzer.getBoard());

        System.out.println("CALCULATOR: \n\tmovesData: "+movesData.toString());
        System.out.println("\tblocksData: "+blocksData.toString());

        /* plansza wyłącznie z nieokreślonymi blokami */
        BoardState boardWithoutBlocks = board.getBoardWithoutBlocks(blocksData);
        BoardAnalyzer boardWithoutBlocksAnalyzer = new BoardAnalyzer(boardWithoutBlocks);
        MovesData movesDataWithoutBlocks = boardWithoutBlocksAnalyzer.findAllMoves();
        System.out.println("FILTERED BOARD: "+movesDataWithoutBlocks.toString());
        /* pozostały same bloki */
        if(movesDataWithoutBlocks.nMoves == 0)
            return findMoveWhenOnlyBlocks(blocksData, board);
        /* pozostały bloki i na tyle mało ruchów, że można stworzyć drzewo */
        if(movesDataWithoutBlocks.nMoves <= TREE_MOVES_LIMIT)
            return findWithMovesTree(board);
        /* stan nieokreślony - za dużo ruchów do obliczeń */
        return findMoveWhenFullBoard(movesData);
    }

    /* wyszukiwanie najlepszego ruchu sposród małej liczby nieokreślonych bloków */
    private static BrickBlock findWithMovesTree(BoardState board){
        System.out.println("func: TREE SEARCH");
        Tree movesTree = new Tree(board);
        movesTree.growTree(3);
        return movesTree.getMatchingMove();
    }

    /* wyszukiwanie najlepszego ruchu, gdy jest za dużo możliwych ruchów */
    private static BrickBlock findMoveWhenFullBoard(MovesData movesData){
        System.out.println("func: FULL BOARD");
        HashSet<BrickBlock> movesSet = BoardAnalyzer.mapValuesToSet(movesData.getMovesMap());
        BrickBlock result = movesSet.iterator().next();
        for(BrickBlock move : movesSet){
            if(move.getMovesReduction() > result.getMovesReduction())
                result = move;
        }
        return result;
    }

    /* wyszukiwanie najlepszego ruchu, gdy pozostały tylko bloki */
    private static BrickBlock findMoveWhenOnlyBlocks(BlocksData blocksData, BoardState board){
        System.out.println("func: BLOCKS ONLY");
        LeadingState leading = blocksData.checkLeadingState();
        ControlState control = blocksData.checkControlState();
        if(leading == LeadingState.ODD){
            if(control == ControlState.ODD){
                return getChangingNothing(blocksData, board);
            }
            else
                return getChangingControl(blocksData, board);
        }
        else
            return getChangingLeadingAndControlState(blocksData, board);
    }

    /* nie zmienia ogólnego stanu gry */
    private static BrickBlock getChangingNothing(BlocksData blocksData, BoardState board){
        System.out.println("\tmove tactics: change nothing");
        if(blocksData.getBlocksType1().size() > 0)
            return blocksData.getBlocksType1().get(0).nextMove(board);
        if(blocksData.getBlocksType2().size() > 0)
            return blocksData.getBlocksType2().get(0).nextMove(board);
        return blocksData.getBlocksType2or1().get(0).leaveOneMove(board);
    }

    /* zmienia stan kontroli */
    private static BrickBlock getChangingControl(BlocksData blocksData, BoardState board){
        System.out.println("\tmove tactics: change control");
        if(blocksData.getBlocksType2or1().size() > 0)
            return blocksData.getBlocksType2or1().get(0).leaveOneMove(board);
        if(blocksData.getBlocksType1().size() > 0)
            return blocksData.getBlocksType1().get(0).nextMove(board);
        return blocksData.getBlocksType2().get(0).nextMove(board);
    }

    /* zmienia jednocześnie stan kontroli i prowadzenia */
    private static BrickBlock getChangingLeadingAndControlState(BlocksData blocksData, BoardState board){
        System.out.println("\tmove tactics: change control and leading");
        if(blocksData.getBlocksType2or1().size() > 0)
            return blocksData.getBlocksType2or1().get(0).leaveZeroMoves(board);
        if(blocksData.getBlocksType1().size() > 0)
            return blocksData.getBlocksType1().get(0).nextMove(board);
        return blocksData.getBlocksType2().get(0).nextMove(board);
    }





}
