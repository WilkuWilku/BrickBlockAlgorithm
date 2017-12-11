package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.BlockTypes;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-14.
 */
public class BloccLauncher {
    public static void main(String[] args) {
        //BoardState board = BoardState.randomBoard(5, 12);
        BoardState board = new BoardState(4, new int[]{1, 2});
        BoardAnalyzer analyzer = new BoardAnalyzer(board);

        long curT = System.nanoTime();
        ArrayList<BrickBlock> list = analyzer.findAllMoves();

        double delta = (double)(System.nanoTime() - curT)/1000000;

        for (BrickBlock block : list) {
            System.out.println(block.toString() + "; MovesLeft: "+(list.size()-block.getMovesReduction()+1));
        }
        board.print();
        System.out.println("Czas: "+delta+"ms, znaleziono "+list.size()+" możliwych ruchów");
        //board.addBrick(new BrickBlock(8, BlockRotation.R90, board));
        System.out.println();
        for (BrickBlock block : list) {
            System.out.println(block.toString() + "; MovesLeft: "+(list.size()-block.getMovesReduction()+1));
        }
        board.print();
        System.out.println("Czas: "+delta+"ms, znaleziono "+list.size()+" możliwych ruchów");
    }
}
