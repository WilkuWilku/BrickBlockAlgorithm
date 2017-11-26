package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.Blocks;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        BoardState board = new BoardState(7, new int[]{1,2, 5, 7, 9,12, 13, 15,19, 20, 22, 24, 25, 26, 27, 30, 32,33, 36, 40, 41, 44, 45, 46});
        //BoardState board = new BoardState(200);
        BoardAnalyzer analyzer = new BoardAnalyzer(board);
        long curT = System.nanoTime();

        for(Blocks types : Blocks.values())
            analyzer.findAllPossibilities(types.getBlockTypeClass());
        double delta = (double)(System.nanoTime() - curT)/1000000;
        ArrayList<AbstractBlock> list = analyzer.getPossibilities();
        //board.print();
        //for(AbstractBlock block : list)
        //    System.out.println(block.toString());
        System.out.println("Czas: "+delta+"ms, znaleziono "+list.size()+" możliwości");
    }
}
