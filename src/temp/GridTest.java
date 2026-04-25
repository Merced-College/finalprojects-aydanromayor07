package temp;
import main.Grid;

import org.junit.Assert;
import org.junit.Test;

public class GridTest {
    public static void main(String[] args) {
        int xSize = 20;
        int ySize = 30;
        // Make and print an empty grid
        Grid grid1 = new Grid(xSize, ySize);
        grid1.printGrid();

        // Open bottom row of squares
        for(int i = 0; i < ySize; i++) {
            grid1.open(0, i);
        }

        System.out.println();
        grid1.printGrid();

        // Set open squares to 5
        for (int i = 0; i < ySize; i++) {
            grid1.setValue(5, 0, i);
        }

        System.out.println();
        grid1.printGrid();

        // Set open squares to mines
        int MINE = 9;
        for (int i = 0; i < ySize; i++) {
            grid1.setValue(MINE, 0, i);
        }

        System.out.println();
        grid1.printGrid();

        // Flag open squares
        for (int i = 0; i < ySize; i++) {
            grid1.flag(0, i);
        }

        System.out.println();
        grid1.printGrid();
    }

    @Test
    public void test() {
        int xSize = 20;
        int ySize = 30;
        Grid grid1 = new Grid(xSize, ySize);
        grid1.printGrid();

        Assert.assertEquals(xSize, grid1.getXSize());
        Assert.assertEquals(ySize, grid1.getYSize());
        Assert.assertEquals(xSize * ySize, grid1.getTotalSquares());
    }
}