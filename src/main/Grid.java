/*
 * Author: Aydan Romayor
 * Class: Grid.java
 * 4/14/2026
 * 
 * Description:
 * This class controls the main game grid. The grid consists of a 2D array of squares.
 * Allows the program to manage an instance of a game and access the individual squares.
 */

package main;

public class Grid {
    // Fields
    public static final int EMPTY = 0;
    public static final int BOMB = 9;

    private final int xSize;
    private final int ySize;
    private final Square[][] grid;

    // Constructors
    public Grid(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        grid = new Square[xSize][ySize];

        initializeSquares();
    }

    // Grid Methods
    private void initializeSquares() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                grid[x][y] = new Square();
            }
        }
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getTotalSquares() {
        return xSize * ySize;
    }


    /*
     * Author: Aydan Romayor
     * Function: printGrid()
     * This method prints each individual square into a grid with labeled axis for easy readability.
     * Time complexity: O(xSize * ySize)
     */


    /**
     * Prints the grid.
     */
    public void printGrid() {
        // Print x-axis labels and grid
        System.out.println("x");
        for (int x = 0; x < xSize; x++) {
            System.out.print(String.valueOf(convertX(x)) + "\t");
            for (int y = 0; y < ySize; y++) {
                System.out.print(grid[x][y] + "  ");
            }
            System.out.println();
        }
        System.out.print("\n\t");

        // Print y-axis labels
        for (int i = 0; i < ySize; i++) {
            System.out.print(i);

            if (i >= 10) System.out.print(" ");
            else System.out.print("  ");
        }
        System.out.println(" y");
    }

    // Square Methods
    private int convertX(int x) {
        return xSize - x - 1; // Converts user inputted x-value into the correct corresponding value in the array
    }

    public void setValue(int value, int x, int y) { grid[convertX(x)][y].setValue(value); }

    public void open(int x, int y) { grid[convertX(x)][y].open(); }

    public void flag(int x, int y) { grid[convertX(x)][y].flag(); }

    public int getValue(int x, int y) { return grid[convertX(x)][y].getValue(); }

    public boolean isEmpty(int x, int y) { return grid[convertX(x)][y].isEmpty(); }

    public boolean isOpen(int x, int y) { return grid[convertX(x)][y].isOpen(); }

    public boolean isFlagged(int x, int y) { return grid[convertX(x)][y].isFlagged(); }

    public boolean isBomb(int x, int y) { return grid[convertX(x)][y].isBomb(); }
}
