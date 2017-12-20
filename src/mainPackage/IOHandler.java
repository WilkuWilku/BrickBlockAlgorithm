package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.Straight3Block;

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

    public Duo getNextMove() {
        String input = scanner.nextLine();
        String[] parts = input.split("x|_");
        int coords[] = new int[4];
        for (int i = 0; i < coords.length; i++)
            coords[i] = Integer.parseInt(parts[i]);

        int index1 = IndexConverter.xyToIndex(coords[0], coords[1], boardSize);
        int index2 = IndexConverter.xyToIndex(coords[2], coords[3], boardSize);

        return new Duo(index1, index2);
    }

    public Duo getNextMove(int n){
        String input = scanner.nextLine();
        String[] parts = input.split("-");
        int index = Integer.parseInt(parts[0]);
        if(parts[1].equals("R0"))
            return new Duo(index, index+1);
        if(parts[1].equals("R90"))
            return new Duo(index, index+boardSize);

        return null;
    }
}
