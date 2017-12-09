package mainPackage;

import mainPackage.blocks.AbstractBlock;
import mainPackage.blocks.EmptyBlock;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Created by Inf on 2017-11-18.
 */
/* klasa przechowująca dane o stanie planszy i umożliwiająca jego zmiany */

public class BoardState {
    public static int size;             // szerokość planszy
    private boolean[] cells;            // true = zajęta, false = wolna
    private ArrayList<Integer> freeCells;


    /* nowa pusta plansza o wielkości size */
    public BoardState(int size){
        this.size = size;
        cells = new boolean[size*size];
        freeCells = new ArrayList<>(size*size);
        for(int i=0; i<size*size; i++)
            freeCells.add(i);
    }
    /* nowa plansza o wielkości size z zablokowanymi polami o indeksach blockedCells[] */
    public BoardState(int size, int[] blockedCells){
        this(size);
        for(int i=0; i<blockedCells.length; i++)
            setCell(blockedCells[i]);
    }

    public int getNFreeCells(){
        return freeCells.size();
    }

    public ArrayList<Integer> getFreeCells() {
        return freeCells;
    }

    /* zaznacza pole o indeksie index */
    public void setCell(int index) throws ArrayIndexOutOfBoundsException {
        if(index >= size*size || index < 0)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [index="+index+", size="+size+"]");
        cells[index] = true;
        freeCells.remove(Integer.valueOf(index));
    }

    public void setCell(int x, int y) throws ArrayIndexOutOfBoundsException {
        if(x<0 || y<0 || x>=size || y>=size)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [x="+x+", y="+y+", size="+size+"]");
        setCell(IndexConverter.xyToIndex(x, y, size));
    }

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

    public boolean getCell(int index){
        if(index<0 || index>=size*size)
            return true;
        return cells[index];
    }

    public boolean getCell(int x, int y) {
        if(x<0 || y<0 || x>=size || y>=size)
            return true;
        return cells[y*size+x];
    }

    /* graficznie przedstawia stan planszy */
    public void print(){
        System.out.println("PLANSZA " + size + "x" + size + "\n");
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(cells[i*size+j])
                    System.out.print("X ");
                else
                    System.out.print("· ");
            }
            System.out.print("\n");
        }
    }

    /* generuje losowo zapełnioną planszę */
    public static BoardState randomBoard(int size, int nBlocked){
        BoardState board = new BoardState(size);
        Random random = new Random();
        for(int i=0; i<nBlocked; i++){
            int index = random.nextInt(size*size);
            while(board.getCell(index))
                index = random.nextInt(size*size);
            board.setCell(index);
        }
        return board;
    }

    public void cleanBoard(){
        ArrayList<Integer> blocksToDelete = new ArrayList();
        for(Integer i : freeCells){
            if(EmptyBlock.isEmpty(this, i)){
                blocksToDelete.add(i);
            }
        }
        freeCells.removeAll(blocksToDelete);
    }

}
