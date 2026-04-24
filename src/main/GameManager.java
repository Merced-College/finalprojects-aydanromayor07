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
     * Function: placeBombs()
     * This takes a grid, and a chosen x-coordinate and y-coordinate, and randomly scatters bombs across the grid without putting them
     * too close to the target coordinate.
     * Time complexity: O(xSize * ySize)
     */
    /**
     * Places bombs randomly around an inputted Grid object. Avoids placing bombs immediately adjacent to the target coordinates.
     * @param x target x-coordinate
     * @param y target y-coordinate
     * @param numBombs number of bombs to place around the map.
     * @param grid input Grid object.
     */
    public static void placeBombs(int x, int y, int numBombs, Grid grid) {
        int[][][] neighbors = getNeighbors(x, y);
        ArrayList<Integer[]> acceptableLocations = new ArrayList<>(); // Acceptable locations for the game to place bombs.

        // Generate a list of all acceptable locations for the bombs. This should be every square except the target coordinates and its neighbors.
        // If the bomb count exceeds the area of the grid - 9 (the size of a 3x3 grid), no need to check neighbors.
        for (int i = 0; i < grid.getXSize(); i++) {
            for (int j = 0; j < grid.getYSize(); j++) {
                int[] currentCoord = {i, j};
                if (!isIn(currentCoord, neighbors)) acceptableLocations.add(new Integer[]{i, j});
            }
        }

        /* Randomly place bombs around the grid using locations from the list of acceptable locations.
        Once the bomb has been set, remove its location from the list so it isn't picked again. */
        for (int i = 0; i < numBombs; i++) {
            int randomIndex = (int)(Math.random() * acceptableLocations.size()); // Generate a random index.
            Integer[] currentCoords = acceptableLocations.get(randomIndex); // Get the coordinate from the index.

            grid.setValue(Grid.BOMB, currentCoords[0], currentCoords [1]); // Set the value of the square at the chose coordinate to a bomb.
            acceptableLocations.remove(randomIndex); // Remove the coordinate from the list.
        }
    }

    /*
     * Author: Aydan Romayor
     * Function: bombNeighborCount()
     * This takes a Grid, and for every empty non-bomb square, counts the bombs adjacent to each Square.
     * It thens sets the value of that Square to the number of adjacent bombs.
     */
    /**
     * For every non-bomb Square, counts the number of bombs adjacent to the Square. It then sets the value of the Square to that number.
     * @param grid Input grid. Use GameManager.placeBombs() to set the bombs.
     */
    public static void bombNeighborCount(Grid grid) {
        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (grid.isBomb(x, y)) continue;

                int[][][] neighbors = GameManager.getNeighbors(x, y);
                grid.setValue(countBombs(neighbors, grid), x, y);
            }
        }
    }

    // Check the number of neighbors of a single tile that are bombs.
    public static int countBombs(int[][][] toCheck, Grid grid) {
        int bombCount = 0;

        for (int i = 0; i < toCheck.length; i++) {
            for (int j = 0; j < toCheck[i].length; j++) {
                int x = toCheck[i][j][0]; // x-coordinate
                int y = toCheck[i][j][1]; // y-coordinate

                boolean valid = validCoordinate(x, y, grid); // Skip if the coordinate is not valid
                if(!valid) continue;

                // If the neighbor is a bomb, add one to the bomb count.
                // No need to skip the target tile itself; bombNeighborCount() already handles that.
                if (grid.isBomb(x, y)) bombCount++;
            }
        }

        return bombCount;
    }

    // Check if a coordinate is valid on a grid.
    public static boolean validCoordinate(int x, int y, Grid grid) {
        if (x < 0 || y < 0) return false;

        if (x >= grid.getXSize() || y >= grid.getYSize()) return false;
        else return true;
    }
}
