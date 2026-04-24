package temp;

import main.GameManager;
import main.Grid;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {
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


        int numBombs = 40;
        Grid grid = new Grid(xSize, ySize);
        GameManager.placeBombs(xCoord, yCoord, numBombs, grid);

        // Should print a mostly empty grid with bombs scattered around the plot.
        int bombCount = 0;
        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (grid.isBomb(x, y)) {
                    grid.open(x, y);
                    bombCount++;
                }
            }
        }

        System.out.println();
        grid.printGrid();
        System.out.println("\nNum bombs: " + bombCount);

        GameManager.bombNeighborCount(grid);

        // Should print a grid with bombs and its adjacent tiles all open. Said adjacent tiles must show the
        // number of bombs adjacent to it. If it is 0, it is close and thus shows "-".
        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (!grid.isEmpty(x, y) && !grid.isBomb(x, y)) {
                    grid.open(x, y);
                }
            }
        }

        System.out.println();
        grid.printGrid();
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
        int numBombs = 300;

        Grid grid = new Grid(xSize, ySize);
        GameManager.placeBombs(xCoord, yCoord, numBombs, grid);

        int bombCount = 0;
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (grid.isBomb(x, y)) bombCount++; 
            }
        }

        Assert.assertEquals(numBombs, bombCount);
        
        Assert.assertEquals(GameManager.validCoordinate(0, 0, grid), true);
        Assert.assertEquals(GameManager.validCoordinate(-1, 0, grid), false);
        Assert.assertEquals(GameManager.validCoordinate(xSize, ySize, grid), false);
        Assert.assertEquals(GameManager.validCoordinate(xSize - 1, ySize - 1, grid), true);
    }
}
