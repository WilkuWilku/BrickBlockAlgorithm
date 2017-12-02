package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.Blocks;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        //BoardState board = new BoardState(7, new int[]{2, 9, 13, 15,19, 20, 22, 24, 25, 26, 27, 30, 32,33, 36, 40, 41, 44, 45, 46});
        BoardState board = new BoardState(8, new int[]{4, 6, 8, 11, 14, 16, 17, 19, 21,22, 25, 27, 30,32, 35,37, 40,41,42, 44, 45, 47,49,52,53, 55, 58,60, 62});
        //BoardState board = new BoardState(420);
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        //long curT = System.nanoTime();
        /*
        for(Blocks types : Blocks.values())
            analyzer.findAllPossibilities(types.getBlockTypeClass());
*/
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.connect();
        analyzer.findPossibilitiesAtIndexWithDB(0);
        db.closeConnection();


        //analyzer.findBoardCoverings(board);
        //double delta = (double)(System.nanoTime() - curT)/1000000;
        //ArrayList list = analyzer.getPossibilities();
        board.print();
        /*for(ArrayList<AbstractBlock> list1 : list) {
            System.out.println("#########");
            for (AbstractBlock block : list1)
                System.out.println(block.toString());
        }

        board.print();*/
        //System.out.println("Czas: "+delta+"ms, znaleziono "+list.size()+" możliwości");
    }
}
