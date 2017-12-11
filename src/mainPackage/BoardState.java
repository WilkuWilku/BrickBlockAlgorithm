package mainPackage;

import mainPackage.blocks.Blocks;
import mainPackage.blocks.blocks1type.BrickBlock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Inf on 2017-11-18.
 */
/* klasa przechowująca dane o stanie planszy i umożliwiająca jego zmiany */

public class BoardState {
    public static int size;             // szerokość planszy
    private int[] cells;            // == 0 -> zajęta;  > 0 -> wolna
    //private ArrayList<Integer> freeCells;


    /* nowa pusta plansza o wielkości size */
    public BoardState(int size) {
        this.size = size;
        cells = new int[size * size];
        //freeCells = new ArrayList<>(size * size);
        //for (int i = 0; i < size * size; i++)
        //    freeCells.add(i);
        for (int index = 0; index < size * size; index++) {
            int x = IndexConverter.xOfIndex(index, size);
            int y = IndexConverter.yOfIndex(index, size);
            if (y == 0 || y == size - 1) {
                if (x == 0 || x == size - 1)
                    cells[index] = 2;
                else
                    cells[index] = 3;
            } else if (x == 0 || x == size - 1)
                cells[index] = 3;
            else
                cells[index] = 4;
        }

    }

    /* nowa plansza o wielkości size z zablokowanymi polami o indeksach blockedCells[] */
    public BoardState(int size, int[] blockedCells) {
        this(size);
        for (int i = 0; i < blockedCells.length; i++)
            setCell(blockedCells[i]);
    }

   /* public int getNFreeCells() {
        return freeCells.size();
    }*/
/*
    public ArrayList<Integer> getFreeCells() {
        return freeCells;
    }
*/
    /* zaznacza pole o indeksie index */
    public void setCell(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= size * size || index < 0)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [index=" + index + ", size=" + size + "]");
        int x = IndexConverter.xOfIndex(index, size);
        int y = IndexConverter.yOfIndex(index, size);
        cells[index] = 0;
        if (x - 1 >= 0)
            decrementCell(IndexConverter.xyToIndex(x - 1, y, size));
        if (x + 1 < size)
            decrementCell(IndexConverter.xyToIndex(x + 1, y, size));
        if (y + 1 < size)
            decrementCell(IndexConverter.xyToIndex(x, y + 1, size));
        if (y - 1 >= 0)
            decrementCell(IndexConverter.xyToIndex(x, y - 1, size));
       // freeCells.remove(Integer.valueOf(index));
    }

    public void setCell(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (x < 0 || y < 0 || x >= size || y >= size)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [x=" + x + ", y=" + y + ", size=" + size + "]");
        setCell(IndexConverter.xyToIndex(x, y, size));
    }

    public void addBrick(BrickBlock brick, BoardAnalyzer analyzer){
        ArrayList<BrickBlock> moves = analyzer.getMoves();
        int index = brick.getReferenceCellIndex();
        for (int i = 0; i < brick.getCells().size(); i++)
            setCell(brick.getCells().get(i));
        //TODO zaktualizować MR BrickBlocków dookoła
    }


    /*
    public void unsetCell(int index) throws ArrayIndexOutOfBoundsException {
        if(index >= size*size || index < 0)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [index="+index+", size="+size+"]");
        cells[index] = false;
        freeCells.add(index);
    }

    public void unsetCell(int x, int y) throws ArrayIndexOutOfBoundsException {
        if(x<0 || y<0 || x>=size || y>=size)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [x="+x+", y="+y+", size="+size+"]");
        unsetCell(IndexConverter.xyToIndex(x, y, size));
    }
    */
    public int getCell(int index) {
        if (index < 0 || index >= size * size)
            return 0;
        return cells[index];
    }

    public void decrementCell(int index) {
        if (cells[index] > 0)
            cells[index]--;
    }

    public int getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size)
            return 0;
        return cells[y * size + x];
    }

    /* graficznie przedstawia stan planszy */
    public void print() {
        System.out.println("PLANSZA " + size + "x" + size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i * size + j] == 0)
                    System.out.print("X ");
                else
                    System.out.print(cells[i * size + j] + " ");
            }
            System.out.print("\n");
        }
    }

    /* generuje losowo zapełnioną planszę */
    public static BoardState randomBoard(int size, int nBlocked) {
        BoardState board = new BoardState(size);
        Random random = new Random();
        for (int i = 0; i < nBlocked; i++) {
            int index = random.nextInt(size * size);
            while (board.getCell(index) == 0)
                index = random.nextInt(size * size);
            board.setCell(index);
        }
        return board;
    }
/*
    public void cleanBoard() {
        ArrayList<Integer> blocksToDelete = new ArrayList();
        for (Integer i : freeCells) {
            if (Blocks.isEmpty(this, i)) {
                blocksToDelete.add(i);
            }
        }
        freeCells.removeAll(blocksToDelete);
    }
*/
    public int[] getCells() {
        return cells;
    }



}
