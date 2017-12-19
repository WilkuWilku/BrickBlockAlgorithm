package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Inf on 2017-11-26.
 */
public class threadTest {

    public static class Work {
        public int it = 0;

        void work(int threads, int id) {
            int n = 0, x = 1000000;
            for (int i = id; i < 40000000; i += threads) {
                n++;
                x--;
                String s = String.valueOf(Math.abs(n+x));
                s.charAt(0);
                s.compareTo("S");
                ArrayList l = new ArrayList();
                l.add(n);
                l.add(x);
                l.add(s);
                l.add(n+x);
                l.remove(0);
                ArrayList l2 = new ArrayList();
                l2.add("sss");
                l2.add("aaa");
                l2.add("dddddddddddddddddddd");
                l2.addAll(l);
                l2.contains(n);
            }
        }
    }



    public static void main(String[] args) {
        BoardState b1 = new BoardState(3);
        BoardAnalyzer analyzer = new BoardAnalyzer(b1);
        BoardState b2 = analyzer.getBoard().copyBoard();
        //b2.addBrick(new BrickBlock(0, BlockRotation.R0, b2), new BoardAnalyzer(b2));
        b1.print();
        System.out.println();
        b2.print();

    }
}
