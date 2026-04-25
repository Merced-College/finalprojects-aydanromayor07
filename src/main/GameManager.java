/*
 * Author: Aydan Romayor
 * Class: GameManager.java
 * 4/21/2026
 * 
 * Description:
 * This class controls various aspects of the game. This includes algorithms and special functions for the squares and grid.
 */


package main;
import java.util.ArrayList;
import java.util.Arrays;

public final class GameManager {

    private GameManager() {} // Private constructor to prevent instantiation.

    /*
     * Author: Aydan Romayor
     * Function: getNeighbors()
     * This method finds all the adjacant tiles of the [targetX, targetY] coordinate, including diagonals.
     * Time complexity: O(1)
     */
    /**
     * Returns a 3x3 grid of [x, y] coordinates centered on targetX and targetY.
     * @param targetX the target x-coordinate
     * @param targetY the target y-coordinate
     * @return A 3x3x2 array 3 rows of 3 columns of [x, y] arrays.
    */
    public static int[][][] getNeighbors(int targetX, int targetY) {
        int[][][] neighbors = new int[3][3][2]; // Creates a 3x3 grid of [x, y] coordinates such that there are 9 [x, y] coords in total.

        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors[i].length ; j++) {
                neighbors[i][j][0] = targetX + (i - 1); // x-coordinate
                neighbors[i][j][1] = targetY + (j - 1); // y-coordinate
            }
        }
        
        return neighbors;
    }

    // This method checks if a coordiate is in an inputted array.
    public static boolean isIn(int[] targetCoord, int[][][] toCheck) {
        for (int i = 0; i < toCheck.length; i++) {
            for (int j = 0; j < toCheck[i].length; j++) {
                int[] currentCoord = toCheck[i][j];
                if (Arrays.equals(currentCoord, targetCoord)) return true;
            }
        }
        return false;
    }

    /*
     * Author: Aydan Romayor
     * Function: placeMines()
     * This takes a grid, and a chosen x-coordinate and y-coordinate, and randomly scatters miness across the grid without putting them
     * too close to the target coordinate.
     * Time complexity: O(xSize * ySize)
     */
    /**
     * Places mines randomly around an inputted Grid object. Avoids placing mines immediately adjacent to the target coordinates as long as
     * numMines does not exceed grid.getTotalSquares() - 9. If it does, skip checking for immediate neighbors.
     * @param x target x-coordinate
     * @param y target y-coordinate
     * @param numMines number of mines to place around the map.
     * @param grid input Grid object.
     */
    public static void placeMines(int x, int y, int numMines, Grid grid) {
        int[][][] neighbors = getNeighbors(x, y);
        ArrayList<Integer[]> acceptableLocations = new ArrayList<>(); // Acceptable locations for the game to place mines.

        // Generate a list of all acceptable locations for the mines. This should be every square except the target coordinates and its neighbors.
        // If the mine count exceeds the area of the grid - 9 (the size of a 3x3 grid), no need to check neighbors.
        for (int i = 0; i < grid.getXSize(); i++) {
            for (int j = 0; j < grid.getYSize(); j++) {
                int[] currentCoord = {i, j};

                if (numMines > grid.getTotalSquares() - 9) {
                    if (i != x || j != y) acceptableLocations.add(new Integer[]{i, j}); // If the number of mines exceeds grid.getTotalSqaures() - 9 AND the current coordinate does not equal the target coordinate, add to acceptableLocations.
                }
                else if (!isIn(currentCoord, neighbors)) acceptableLocations.add(new Integer[]{i, j}); // If the number of mines does not exceed grid.getTotalSquares() - 9, check for neighbors as usual.
            }
        }

        /* Randomly place mines around the grid using locations from the list of acceptable locations.
        Once the mine has been set, remove its location from the list so it isn't picked again. */
        for (int i = 0; i < numMines; i++) {
            int randomIndex = (int)(Math.random() * acceptableLocations.size()); // Generate a random index.
            Integer[] currentCoords = acceptableLocations.get(randomIndex); // Get the coordinate from the index.

            grid.setValue(Grid.MINE, currentCoords[0], currentCoords [1]); // Set the value of the square at the chose coordinate to a mine.
            acceptableLocations.remove(randomIndex); // Remove the coordinate from the list.
        }
    }

    /*
     * Author: Aydan Romayor
     * Function: mineNeighborCount()
     * This takes a Grid, and for every empty non-mine square, counts the mines adjacent to each Square.
     * It thens sets the value of that Square to the number of adjacent mines.
     */
    /**
     * For every non-mine Square, counts the number of mines adjacent to the Square. It then sets the value of the Square to that number.
     * @param grid Input grid. Use GameManager.placemines() to set the mines.
     */
    public static void mineNeighborCount(Grid grid) {
        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (grid.isMine(x, y)) continue;

                int[][][] neighbors = GameManager.getNeighbors(x, y);
                grid.setValue(countMines(neighbors, grid), x, y);
            }
        }
    }

    // Check the number of neighbors of a single tile that are mines.
    public static int countMines(int[][][] toCheck, Grid grid) {
        int mineCount = 0;

        for (int i = 0; i < toCheck.length; i++) {
            for (int j = 0; j < toCheck[i].length; j++) {
                int x = toCheck[i][j][0]; // x-coordinate
                int y = toCheck[i][j][1]; // y-coordinate

                boolean valid = !outOfBounds(x, y, grid); // Skip if the coordinate is not valid
                if(!valid) continue;

                // If the neighbor is a mine, add one to the mine count.
                // No need to skip the target tile itself; mineNeighborCount() already handles that.
                if (grid.isMine(x, y)) mineCount++;
            }
        }

        return mineCount;
    }

    // Check if a coordinate is valid on a grid.
    public static boolean outOfBounds(int x, int y, Grid grid) {
        if (x < 0 || y < 0) return true;

        return x >= grid.getXSize() || y >= grid.getYSize();
    }

    /*
     * Author: Aydan Romayor
     * Function: floodFill()
     * 
     * This function takes a coordinate, and performs a flood fill algorithm to open all adjacent empty squares and their neighbors.
     * Instead of using recursion, it uses a queue to track which squares to open. This is to avoid stack overflow errors that could occur with large grids and many empty squares.
     * Time complexity: O(xSize * ySize) in the worst case.
     */
    /**
     * Performs a flood fill algorithm to open all adjacent empty squares and their neighbors.
     * @param x the target x-coordinate.
     * @param y the target y-coordinate.
     * @param grid the input grid. Use GameManager.mineNeighborCount() to set the values of the squares before using this method.
     */
    public static void floodFill(int x, int y, Grid grid) {
        ArrayList<Integer[]> queue = new ArrayList<>();

        Integer[] originalCoords = {x, y};
        queue.add(originalCoords);

        while(!queue.isEmpty()) {
            Integer[] currentCoords = queue.getFirst();
            int currentX = currentCoords[0];
            int currentY = currentCoords[1];

            queue.removeFirst();
            grid.open(currentX, currentY);

            if (grid.isEmpty(currentX, currentY)) {
                Integer[] north = {currentX + 1, currentY};
                Integer[] east = {currentX, currentY + 1};
                Integer[] south = {currentX - 1, currentY};
                Integer[] west = {currentX, currentY - 1};

                if (validCoordinate(north[0], north[1], grid)) queue.add(north);
                if (validCoordinate(east[0], east[1], grid)) queue.add(east);
                if (validCoordinate(south[0], south[1], grid)) queue.add(south);
                if (validCoordinate(west[0], west[1], grid)) queue.add(west);

                Integer[] ne = {currentX + 1, currentY + 1};
                Integer[] se = {currentX - 1, currentY + 1};
                Integer[] sw = {currentX - 1, currentY - 1};
                Integer[] nw = {currentX + 1, currentY - 1};

                if (validCoordinate(ne[0], ne[1], grid)) queue.add(ne);
                if (validCoordinate(se[0], se[1], grid)) queue.add(se);
                if (validCoordinate(sw[0], sw[1], grid)) queue.add(sw);
                if (validCoordinate(nw[0], nw[1], grid)) queue.add(nw);
            }
        }
    }

    // Helper method for flood fill to check if a coordinate is valid for the flood fill algorithm.
    public static boolean validCoordinate(int x, int y, Grid grid) {
        return !outOfBounds(x, y, grid) && !grid.isOpen(x, y) && !grid.isFlagged(x, y) && !grid.isMine(x, y);
    }
}
