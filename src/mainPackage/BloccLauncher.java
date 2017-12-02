package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.Blocks;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        //BoardState board = new BoardState(7, new int[]{0, 2,5, 7,8, 9,11, 13, 15,19, 20, 22, 24, 25, 26, 27, 30, 32,33, 36, 40, 41, 44, 45, 46});
        BoardState board = new BoardState(8, new int[]{4, 6, 8, 11, 14, 16, 17, 19, 21,22, 25, 27, 30,32, 35,37, 40,41,42, 44, 45, 47,49,52,53, 55, 58,60, 62});
        //BoardState board = new BoardState(4);
        BoardAnalyzer analyzer = new BoardAnalyzer(board);

/*
        for(Blocks types : Blocks.values())
            analyzer.findAllPossibilities(types.getBlockTypeClass());
*/
        DatabaseHandler db = DatabaseHandler.getInstance();
        db.connect();
        long curT = System.nanoTime();
        ArrayList<AbstractBlock> list = analyzer.findPossibilitiesWithDB();
        int reducibles = analyzer.getNReducibles();
        int blockibles = analyzer.getNBlockibles();
        double delta = (double)(System.nanoTime() - curT)/1000000;
        db.closeConnection();


        //analyzer.findBoardCoverings(board);

        //ArrayList<AbstractBlock> list = analyzer.getPossibilities();
        //board.print();

            for (AbstractBlock block : list) {
                System.out.println(block.toString());
            }
        board.print();
        System.out.println("Czas: "+delta+"ms, znaleziono "+list.size()+" możliwości");
        System.out.println(blockibles + " możliwych do zablokowania, "+reducibles+" możwliwych do zredukowania");
    }
}
