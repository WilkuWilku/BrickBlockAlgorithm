package mainPackage;

import mainPackage.blocks.blocks1type.AbstractBlockType1;
import mainPackage.blocks.blocks1type.BrickBlock;
import mainPackage.blocks.blocks2or1type.AbstractBlockType2or1;
import mainPackage.blocks.blocks2type.AbstractBlockType2;
import mainPackage.blocks.blocks2type.InvertedLongThunderBlock;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Inf on 2017-11-18.
 */
/* klasa przechowująca dane o stanie planszy i umożliwiająca jego zmiany */

public class BoardState {
    public static int size;             // szerokość planszy
    private int[] cells;            // cells[i] == 0 -> zajęta;  cells[i] > 0 -> wolna

    /* nowa pusta plansza o wielkości size */
    public BoardState(int size) {
        this.size = size;
        cells = new int[size * size];
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
    public BoardState(int size, ArrayList<Integer> blockedCells) {
        this(size);
        try {
            for (int i = 0; i < blockedCells.size(); i++)
                setCell(blockedCells.get(i));
            } catch (Exception e) {
                System.err.println("BoardState():");
                e.printStackTrace();
            }
    }

    /* tworzy nową planszę będącą kopią planszy origin */
    public BoardState(BoardState origin){
        this.size = (int)Math.sqrt(origin.cells.length);
        this.cells = new int[origin.cells.length];
        System.arraycopy(origin.cells, 0, this.cells, 0, origin.cells.length);
    }

    /* zaznacza pole o indeksie index */
    public void setCell(int index) throws Exception {
        if (index >= size * size || index < 0)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [index=" + index + ", size=" + size + "]");
        int x = IndexConverter.xOfIndex(index, size);
        int y = IndexConverter.yOfIndex(index, size);
        if(cells[index] == 0)
                throw new Exception("NIEDOZWOLONY RUCH: komórka jest już zajęta! [index="+index+"]");

        cells[index] = 0;
        if (x - 1 >= 0)
            decrementCell(IndexConverter.xyToIndex(x - 1, y, size));
        if (x + 1 < size)
            decrementCell(IndexConverter.xyToIndex(x + 1, y, size));
        if (y + 1 < size)
            decrementCell(IndexConverter.xyToIndex(x, y + 1, size));
        if (y - 1 >= 0)
            decrementCell(IndexConverter.xyToIndex(x, y - 1, size));
    }

    public void setCell(int x, int y) throws Exception {
        if (x < 0 || y < 0 || x >= size || y >= size)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [x=" + x + ", y=" + y + ", size=" + size + "]");
        setCell(IndexConverter.xyToIndex(x, y, size));
    }
/*
    public void addBrick(BrickBlock brick) {
        for (int i = 0; i < brick.getCells().size(); i++)
            try {
                setCell(brick.getCells().get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }*/

    public void addBrick(BrickBlock brick){
        for(int i=0; i<brick.getShape().length; i++){
            try {
                setCell(brick.getReferenceCellIndex() + brick.getShape()[i][0] + brick.getShape()[i][1]*size);
            } catch (Exception e) {
                //System.err.println("addBrick():");
                //e.printStackTrace();
            }
        }
    }

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
                    System.out.print(". ");
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
            try {
                board.setCell(index);
            } catch (Exception e) {
                System.err.println("randomBoard():");
                e.printStackTrace();
            }
        }
        return board;
    }

    public int[] getCells() {
        return cells;
    }

    /* zwraca planszę po odfiltrowaniu wszystkich bloków podanych w blocksData */
    public BoardState getBoardWithoutBlocks(BlocksData blocksData){
        BoardState resultBoard = new BoardState(this);
        for(AbstractBlockType1 block1 : blocksData.getBlocksType1()){
            block1.markOnBoard(resultBoard);
        }
        for(AbstractBlockType2 block2 : blocksData.getBlocksType2()){
            block2.markOnBoard(resultBoard);
        }
        for(AbstractBlockType2or1 block2or1 : blocksData.getBlocksType2or1()){
            block2or1.markOnBoard(resultBoard);
        }
        return resultBoard;
    }
}
