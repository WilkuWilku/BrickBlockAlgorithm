package mainPackage;

import java.util.Scanner;

/**
 * Created by Inf on 2017-11-18.
 */
public class IOHandler {
    private Scanner scanner;
    private int boardSize;
    public IOHandler(int boardSize){
        scanner = new Scanner(System.in);
        this.boardSize = boardSize;
    }

    public Dual getNextMove() {
        String input = scanner.nextLine();
        String[] parts = input.split("x|_");
        int coords[] = new int[4];
        for (int i = 0; i < coords.length; i++)
            coords[i] = Integer.parseInt(parts[i]);

        int index1 = IndexConverter.xyToIndex(coords[0], coords[1], boardSize);
        int index2 = IndexConverter.xyToIndex(coords[2], coords[3], boardSize);

        return new Dual(index1, index2);
    }
}
