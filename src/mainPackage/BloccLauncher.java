package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockTypes;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        //BoardState board = new BoardState(7, new int[]{0, 2,5, 7,8, 9,11, 13, 15,19, 20, 22, 24, 25, 26, 27, 30, 32,33, 36, 40, 41, 44, 45, 46});
        long curT = System.nanoTime();
        BoardState board = BoardState.randomBoard(999, 0);
        //BoardState board = new BoardState(4, new int[]{1, 2, 3, 7, 8, 14, 15});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);


        board.cleanBoard();
        /*for(BlockTypes types : BlockTypes.values())
            analyzer.findAllPossibilities(types.getBlockTypeClass());
*/
        analyzer.findAllMoves();
        ArrayList<AbstractBlock> list = analyzer.getPossibilities();

        int reducibles = analyzer.getNReducibles();
        int blockibles = analyzer.getNBlockibles();
        double delta = (double)(System.nanoTime() - curT)/1000000;
        //analyzer.findBoardCoverings(board);
        for (AbstractBlock block : list) {
            //System.out.println(block.toString());
        }
       // board.print();

        System.out.println("Czas: "+delta+"ms, znaleziono "+list.size()+" ruchów");
        System.out.println(blockibles + " możliwych do zablokowania, "+reducibles+" możwliwych do zredukowania");
        System.out.println(board.getNFreeCells() + " wolnych komórek");
    }
}
