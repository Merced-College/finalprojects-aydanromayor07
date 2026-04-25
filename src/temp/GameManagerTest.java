package temp;

import main.GameManager;
import main.Grid;

import org.junit.Assert;
import org.junit.Test;

public class GameManagerTest {
    public static void main(String[] args) {
        int xCoord = 1;
        int yCoord = 1;

        int[][][] neighbors = GameManager.getNeighbors(xCoord, yCoord);

        // Should print: [0, 0] [0, 1] [0, 2] [1, 0] [1, 1] [1, 2] [2, 0] [2, 1] [2, 2]
        for (int x = 0; x < neighbors.length; x++) {
            for (int y = 0; y < neighbors[x].length; y++) {
                System.out.printf("[%d, %d] ", neighbors[x][y][0], neighbors[x][y][1]);
            }
        }
        System.out.println();

        int xSize = 16;
        int ySize = 30;

        xCoord = 10;
        yCoord = 10;


        int numMines = 40;
        Grid grid = new Grid(xSize, ySize);
        GameManager.placeMines(xCoord, yCoord, numMines, grid);

        // Should print a mostly empty grid with mines scattered around the plot. Should also correctly count all mines.
        int mineCount = 0;
        grid.openAllMines();

        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (grid.isMine(x, y)) mineCount++;
            }
        }

        System.out.println();
        grid.printGrid();
        System.out.println("\nNum mines: " + mineCount);

        GameManager.mineNeighborCount(grid);

        // Should print a grid with mines and its adjacent tiles all open. Said adjacent tiles must show the
        // number of mines adjacent to it. If it is 0, it is closed and thus shows "-".
        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (!grid.isEmpty(x, y) && !grid.isMine(x, y)) {
                    grid.open(x, y);
                }
            }
        }

        System.out.println();
        grid.printGrid();

        // This grid should show the mines surrounding the middle, but not on it or adjacent to it.
        Grid grid2 = new Grid(5, 5);
        GameManager.placeMines(2, 2, 16, grid2);

        grid2.openAllMines();
        System.out.println();
        grid2.printGrid();

        // Because the number of mines is now larger than grid3.getTotalSquares() - 9, the program should scatter
        // mines WITHOUT accounting for being adjacent to the middle (in other words, mines are free to be next to the middle, the target tile, but not on the middle itself)
        Grid grid3 = new Grid(5, 5);
        GameManager.placeMines(2, 2, 17, grid3);

        grid3.openAllMines();
        System.out.println();
        grid3.printGrid();

        // Because the number of mines is one less than the total number of squares, the mines should spawn everywhere EXCEPT the target tile (the middle).
        Grid grid4 = new Grid(5, 5);
        GameManager.placeMines(2, 2, grid4.getTotalSquares() - 1, grid4);

        grid4.openAllMines();
        System.out.println();
        grid4.printGrid();
    }

    @Test
    public void test() {
        int xCoord = 1;
        int yCoord = 1;
        int[][][] neighbors = GameManager.getNeighbors(xCoord, yCoord);

        Assert.assertEquals(GameManager.isIn(new int[]{0, 0}, neighbors), true);
        Assert.assertEquals(GameManager.isIn(new int[]{0, 1}, neighbors), true);
        Assert.assertEquals(GameManager.isIn(new int[]{0, 2}, neighbors), true);

        Assert.assertEquals(GameManager.isIn(new int[]{1, 0}, neighbors), true);
        Assert.assertEquals(GameManager.isIn(new int[]{1, 1}, neighbors), true);
        Assert.assertEquals(GameManager.isIn(new int[]{1, 2}, neighbors), true);

        Assert.assertEquals(GameManager.isIn(new int[]{2, 0}, neighbors), true);
        Assert.assertEquals(GameManager.isIn(new int[]{2, 1}, neighbors), true);
        Assert.assertEquals(GameManager.isIn(new int[]{2, 2}, neighbors), true);

        int xSize = 50;
        int ySize = 50;
        int numMines = 300;

        Grid grid = new Grid(xSize, ySize);
        GameManager.placeMines(xCoord, yCoord, numMines, grid);

        int mineCount = 0;
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (grid.isMine(x, y)) mineCount++; 
            }
        }

        Assert.assertEquals(numMines, mineCount);
        
        Assert.assertEquals(GameManager.outOfBounds(0, 0, grid), false);
        Assert.assertEquals(GameManager.outOfBounds(-1, 0, grid), true);
        Assert.assertEquals(GameManager.outOfBounds(xSize, ySize, grid), true);
        Assert.assertEquals(GameManager.outOfBounds(xSize - 1, ySize - 1, grid), false);
    }
}
