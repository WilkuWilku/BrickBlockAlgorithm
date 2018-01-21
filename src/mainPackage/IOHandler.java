package mainPackage;

import mainPackage.blocks.BlockRotation;
import mainPackage.blocks.blocks1type.BrickBlock;
import mainPackage.blocks.blocks1type.Straight3Block;

import java.util.Scanner;

/**
 * Created by Inf on 2017-11-18.
 */

/* klasa służąca do komunikacji algorytmu z programem sędziującym */
public class IOHandler {
    private Scanner scanner;
    private int boardSize;                      // rozmiar planszy
    public IOHandler(){
        scanner = new Scanner(System.in);
    }


    /* metoda do pobrania danych o ruchu przeciwnika lub komend od sędziego */
    /* zwraca true, gdy gra jest kontynuowana; false, gdy sędzia kończy grę */
    public boolean getNextMove(BoardState board, long initTime) {
        String input = scanner.nextLine();

        if(input.equals("stop") || input.equals("STOP")){
            scanner.close();
            return false;
        }
        if(input.equals("start") || input.equals("START"))
            return true;
        initTime = System.currentTimeMillis();
        String[] parts = input.split("x|_");
        int coords[] = new int[4];
        for (int i = 0; i < coords.length; i++)
            coords[i] = Integer.parseInt(parts[i]);
        int index1 = IndexConverter.xyToIndex(coords[0], coords[1], boardSize);
        int index2 = IndexConverter.xyToIndex(coords[2], coords[3], boardSize);
        board.setCell(index1);
        board.setCell(index2);
        return true;
    }

    /* metoda wczytująca dane o planszy od sędziego, tworząca obiekt planszy i wypełniająca ją odpowiednimi komórkami */
    public BoardState getInitInput(){
        String input = scanner.nextLine();
        String[] parts = input.split("x|_");
        boardSize = Integer.parseInt(parts[0]);
        BoardState board = new BoardState(boardSize);
        for(int i=1; i<parts.length; i+=2){
                int x = Integer.parseInt(parts[i]);
                int y = Integer.parseInt(parts[i+1]);
                board.setCell(x, y);
        }
        return board;
    }

    /* procedura wysyłająca sędziemu komunikat gotowości do walki */
    public void approveInit(){
        System.out.println("OK");
    }

    /* procedura wysyłająca sędziemu dane o następnym ruchu algorytmu */
    public void writeNextStep(BrickBlock block){
        int index1 = block.getReferenceCellIndex();
        int index2 = block.getRotation() == BlockRotation.R0 ? index1+1 : index1+boardSize;
        int x1 = IndexConverter.xOfIndex(index1, boardSize);
        int y1 = IndexConverter.yOfIndex(index1, boardSize);
        int x2 = IndexConverter.xOfIndex(index2, boardSize);
        int y2 = IndexConverter.yOfIndex(index2, boardSize);
        System.out.println(x1+"x"+y1+"_"+x2+"x"+y2);
    }
}
