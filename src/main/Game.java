/*
 * Author: Aydan Romayor
 * Class: Game.java
 * 4/21/2026
 * 
 * Description:
 * This class controls various aspects of the game. This includes algorithms and special functions for the squares and grid.
 */


package main;
import java.util.ArrayList;
import java.util.Arrays;

public final class Game {

    private Game() {} // Private constructor to prevent instantiation.

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
}
