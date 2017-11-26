package mainPackage;

import mainPackage.blocks.AbstractBlock;

import java.util.Set;

/**
 * Created by Inf on 2017-11-18.
 */
/* klasa przechowująca dane o stanie planszy i umożliwiająca jego zmiany */

public class BoardState {
    public static int size;             // szerokość planszy
    private boolean[] cells;

    /* nowa pusta plansza o wielkości size */
    public BoardState(int size){
        this.size = size;
        cells = new boolean[size*size];
    }
    /* nowa plansza o wielkości size z zablokowanymi polami o indeksach blockedCells[] */
    public BoardState(int size, int[] blockedCells){
        this(size);
        for(int i=0; i<blockedCells.length; i++)
            setCell(blockedCells[i]);
    }

    /* zaznacza pole o indeksie index */
    public void setCell(int index) throws ArrayIndexOutOfBoundsException {
        if(index >= size*size || index < 0)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [index="+index+", size="+size+"]");
        cells[index] = true;
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
    }

    public void unsetCell(int x, int y) throws ArrayIndexOutOfBoundsException {
        if(x<0 || y<0 || x>=size || y>=size)
            throw new ArrayIndexOutOfBoundsException("Błąd: Odwołanie do pola poza planszą [x="+x+", y="+y+", size="+size+"]");
        unsetCell(IndexConverter.xyToIndex(x, y, size));
    }

    public boolean getCell(int index){
        return cells[index];
    }

    public boolean getCell(int x, int y) {
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

}
