package mainPackage;

import mainPackage.blocks.BlockFinder;
import mainPackage.blocks.Blocks;
import mainPackage.blocks.Move;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.HashSet;

/**
 * Created by Inf on 2017-12-13.
 */
/* klasa obliczająca optymalny następny ruch */

public final class MoveCalculator {

    private static final int BLOCK_SEARCH_LIMIT = 2000;                         // maksymalna liczba ruchów do rozpoczęcia wyszukiwania bloków
    private static final int TREE_MOVES_LIMIT = 25;                             // maksymalna liczba dzieci korzenia drzewa
    private static final long FULL_BOARD_TIME_LIMIT = 420;                      // limit czasowy wyszukiwania ruchu przy pełnej planszy
    private static boolean BLOCKS_ONLY_LEFT = false;                            // zmienna określająca czy algorytm działa wyłącznie na blokach

    private MoveCalculator(){
    }

    public static BrickBlock nextMove(BoardState board, long initTime){
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        BrickBlock result = null;
        //if(!BLOCKS_ONLY_LEFT) {
        MovesData movesData = analyzer.findAllMoves();

        if (movesData.nMoves < BLOCK_SEARCH_LIMIT) {
            BlocksData blocksData = BlockFinder.searchForBlocks(board);

                /* plansza wyłącznie z nieokreślonymi blokami */
            BoardState boardWithoutBlocks = board.getBoardWithoutBlocks(blocksData);
            BoardAnalyzer boardWithoutBlocksAnalyzer = new BoardAnalyzer(boardWithoutBlocks);
            MovesData movesDataWithoutBlocks = boardWithoutBlocksAnalyzer.findAllMoves();

                 /* pozostały same bloki */
            if (movesDataWithoutBlocks.nMoves == 0) {
                result = findMoveWhenOnlyBlocks(blocksData, board);
                if (!BLOCKS_ONLY_LEFT)
                    BLOCKS_ONLY_LEFT = true;
                return result;
            }
                /* pozostały bloki i na tyle mało ruchów, że można stworzyć drzewo */
            if (movesDataWithoutBlocks.nMoves <= TREE_MOVES_LIMIT) {
                result = findWithMovesTree(boardWithoutBlocks, blocksData, movesDataWithoutBlocks, initTime);
                return result;
            }
        }

            /* gdy nMoves > BLOCK_SEARCH_LIMIT lub nMovesWithoutBlocks > TREE_MOVES_LIMIT */
        return findMoveWhenFullBoard(movesData, initTime);
        //} else {
            /* przejdź od razu do bloków bez wyszukiwania ruchów */
        //    BlocksData blocksData = BlockFinder.searchForBlocks(board);
         //   return findMoveWhenOnlyBlocks(blocksData, board);
        //}
    }


    /* wyszukiwanie najlepszego ruchu sposród małej liczby nieokreślonych bloków */
    private static BrickBlock findWithMovesTree(BoardState board, BlocksData blocksData, MovesData movesData, long initTime){
        //System.out.println("func: TREE SEARCH");
        Tree movesTree = new Tree(board, blocksData, movesData, initTime);
        movesTree.growTree(3, initTime);
        BrickBlock result = movesTree.getMatchingMove(initTime);
        return result;

    }

    /* wyszukiwanie najlepszego ruchu, gdy jest za dużo możliwych ruchów */
    private static BrickBlock findMoveWhenFullBoard(MovesData movesData, long initTime){
        //System.out.println("func: FULL BOARD");
        HashSet<BrickBlock> movesSet = BoardAnalyzer.mapValuesToSet(movesData.getMovesMap());
        BrickBlock result = movesSet.iterator().next();
        for(BrickBlock move : movesSet){
            if(move.getMovesReduction() > result.getMovesReduction())
                result = move;
            if(System.currentTimeMillis() - initTime > FULL_BOARD_TIME_LIMIT)
                return result;
        }
        return result;
    }

    /* wyszukiwanie najlepszego ruchu, gdy pozostały tylko bloki */
    private static BrickBlock findMoveWhenOnlyBlocks(BlocksData blocksData, BoardState board){
        //System.out.println("func: BLOCKS ONLY");
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
            return getChangingLeading(blocksData, board);
    }

    /* nie zmienia ogólnego stanu gry */
    private static BrickBlock getChangingNothing(BlocksData blocksData, BoardState board){
        //System.out.println("\tmove tactics: change nothing");
        if(blocksData.getBlocksType2or1().size() > 0)
            return blocksData.getBlocksType2or1().get(0).leaveOneMove(board);
        if(blocksData.getBlocksType1().size() > 0)
            return blocksData.getBlocksType1().get(0).nextMove(board);
        return blocksData.getBlocksType2().get(0).nextMove(board);
    }

    /* zmienia stan kontroli */
    private static BrickBlock getChangingControl(BlocksData blocksData, BoardState board){
        //System.out.println("\tmove tactics: change control");
        if(blocksData.getBlocksType2().size() > 0)
            return blocksData.getBlocksType2().get(0).nextMove(board);
        if(blocksData.getBlocksType1().size() > 0)
            return blocksData.getBlocksType1().get(0).nextMove(board);
        return blocksData.getBlocksType2or1().get(0).leaveOneMove(board);
    }

    /* zmienia jednocześnie stan kontroli i prowadzenia */
    private static BrickBlock getChangingLeading(BlocksData blocksData, BoardState board){
        //System.out.println("\tmove tactics: change leading");
        if(blocksData.getBlocksType2or1().size() > 0)
            return blocksData.getBlocksType2or1().get(0).leaveZeroMoves(board);
        if(blocksData.getBlocksType1().size() > 0)
            return blocksData.getBlocksType1().get(0).nextMove(board);
        return blocksData.getBlocksType2().get(0).nextMove(board);
    }
}
