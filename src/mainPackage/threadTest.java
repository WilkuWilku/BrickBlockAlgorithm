package mainPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Work w = new Work();
        Thread t0 = new Thread(){
            @Override
            public void run() {
                w.work(4, 0);
            }
        };

        Thread t1 = new Thread(){
            @Override
            public void run() {
                w.work(4, 1);
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                w.work(4, 2);
            }
        };

        Thread t3 = new Thread(){
            @Override
            public void run() {
                w.work(4, 3);
            }
        };

        long curT = System.currentTimeMillis();
        executor.execute(t0);
        executor.execute(t1);
        executor.execute(t2);
        executor.execute(t3);
        executor.shutdown();
        while(!executor.isTerminated()){}
        /*t0.start();
        t1.start();
        t2.start();
        t3.start();
        try {
            t0.join();
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        long delta = System.currentTimeMillis() - curT;
        System.out.println("Czas: "+delta+" Wykonano iteracji: "+w.it);



    }
}
