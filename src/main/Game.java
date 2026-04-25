/*
 * Author: Aydan Romayor
 * Class: Game.java
 * 4/24/2026
 * 
 * Description:
 * Controls an instance of a game. Controls aspects like the username, game mode, and values such as grid size and mine count.
 */

package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    // Gamemodes
    private static final ArrayList<String> modes = new ArrayList<>() {{
        add("Easy");
        add("Medium");
        add("Hard");
        add("Custom");
    }};

    // Preset values: [xSize, ySize, mineCount]
    private static final int[] EASY_PRESET = {9, 9, 10};
    private static final int[] MEDIUM_PRESET = {16, 16, 40};
    private static final int[] HARD_PRESET = {16, 30, 99};

    private final String username;
    private int mode;
    private double time;
    private Grid grid;
    private int flagsLeft;

    private long startTime;
    private long endTime;

    // Ruleset values: [xSize, ySize, mineCount]
    private int[] ruleset;

    private final Scanner scnr;

    public Game(String username) {
        this.username = username;
        ruleset = new int[3];
        scnr = new Scanner(System.in);
    }

    /*
    * Author: Aydan Romayor
    * Function: runGame()
    * 
    * Description:
    * This is the method that controls the actual game itself.
    */
    public void runGame() {
        mode = getUserMode() - 1; // First get the user's chosen gamemode. Subtract 1 to get the correct index in the modes array.

        ruleset = switch(mode) {
            case 0 -> EASY_PRESET;
            case 1 -> MEDIUM_PRESET;
            case 2 -> HARD_PRESET;
            default -> customRuleset(); // If the user chooses the custom ruleset, call the customRuleset() method to get their custom ruleset.
        };

        printRuleset();

        // Make the game grid.
        grid = new Grid(ruleset[0], ruleset[1]);
        flagsLeft = ruleset[2];

        System.out.println();
        grid.printGrid();

        System.out.printf("\nChoose a starting x (0 - %d):\n", grid.getXSize() - 1);
        int startX = userInput(0, grid.getXSize() - 1);

        System.out.printf("\nChoose a starting y (0 - %d):\n", grid.getYSize() - 1);
        int startY = userInput(0, grid.getYSize() - 1);

        GameManager.placeMines(startX, startY, ruleset[2], grid);
        GameManager.mineNeighborCount(grid);

        grid.open(startX, startY);
        GameManager.floodFill(startX, startY, grid);

        startTime = System.nanoTime();
        int choice;
        while (true) {
            if (checkWin()) {
                endTime = System.nanoTime();
                win();
                break;
            }

            System.out.println();
            grid.printGrid();
            System.out.printf("\nFlags left: %d\n", flagsLeft);

            System.out.printf("\nChoose an x (0 - %d):\n", grid.getXSize() - 1);
            int x = userInput(0, grid.getXSize() - 1);

            System.out.printf("\nChoose a y (0 - %d):\n", grid.getYSize() - 1);
            int y = userInput(0, grid.getYSize() - 1);


            if (!grid.isOpen(x, y)) {
                System.out.println("\nChoose an option:");
                System.out.println("> 1. Open Square");
                System.out.println("> 2. Toggle Flag");
                System.out.println("> 3. Change Square");

                choice = userInput(1, 3);

                if (choice == 1) {
                    if (grid.isMine(x, y) && !grid.isFlagged(x, y)) {
                        endTime = System.nanoTime();
                        lose();
                        break;
                    }
                    else if (grid.isFlagged(x, y)) {
                        System.out.println("\nThat square is flagged. Please unflag it before opening.");
                        continue;
                    }

                    GameManager.floodFill(x, y, grid);
                }
                else if (choice == 2) {
                    grid.flag(x, y);

                    if (grid.isFlagged(x, y)) flagsLeft--;
                    else flagsLeft++;
                }
            }
            else {
                System.out.println("\nThat square is already open. Please choose another square.");
            }
        }
    }

    public String getUsername() {
        return username;
    }

    // Asks the user to input a gamemode.
    private int getUserMode() {
        System.out.println("\nChoose a difficulty:");
        System.out.println("> 1. Easy (9x9, 10 mines)");
        System.out.println("> 2: Medium (16x16, 30 mines)");
        System.out.println("> 3: Hard (16x30, 99 mines)");
        System.out.println("> 4: Custom Ruleset");

        int choice = userInput(1, 4);
        return choice;
    }

    // Lets the user choose a custom ruleset.
    private int[] customRuleset() {
        int[] userRuleset = new int[3];

        // Custom x-size of the grid
        System.out.println("\nEnter x (height) size (minimum 5): ");
        userRuleset[0] = userInput(5, -1);
        
        // Custom y-size of the grid
        System.out.println("\nEnter y (length) size (minimum 5): ");
        userRuleset[1] = userInput(5, -1);

        // The maximum number of bombs must be one less than the area of the grid
        int maxMines = userRuleset[0] * userRuleset[1] - 1;
        System.out.printf("\nEnter number of mines (minimum 1, maximum %d): \n", maxMines);
        userRuleset[2] = userInput(1, maxMines);

        return userRuleset;
    }

    // Helper function that allows the user to input their values without problem.
    private int userInput(int min, int max) {
        int n;

        while (true) {
            System.out.print("> ");

            try {
                n = scnr.nextInt();

                if (n < min) continue;
                if (max != -1 && n > max) continue;

            } catch (InputMismatchException e) {
                scnr.next();
                continue;
            }

            break;
        }

        return n;
    }

    // Print the user's rulset.
    private void printRuleset() {
        System.out.println();
        System.out.println("x-size (height): " + ruleset[0]);
        System.out.println("y-size (length): " + ruleset[1]);
        System.out.println("Number of mines: " + ruleset[2]);
    }

    // Check if the win condition has been met.
    // The win condition is met when the number of open non-mine squares is equal to the total number of non-mine squares on the grid.
    private boolean checkWin() {
        int nonMineCounter = 0;

        for (int x = 0; x < grid.getXSize(); x++) {
            for (int y = 0; y < grid.getYSize(); y++) {
                if (!grid.isMine(x, y) && grid.isOpen(x, y)) nonMineCounter++;
            }
        }

        return nonMineCounter == grid.getTotalSquares() - ruleset[2]; // Check if the number of non mine squares is equal to the total number of squares minus the number of mines.
    }

    // Lose function. Called when the user opens a mine.
    private void lose() {
        grid.openAllMines();
        System.out.println();
        grid.printGrid();
        System.out.println("\nYou lose! ");
    }

    private void win() {
        time = (endTime - startTime) / 1_000_000_000.0;
        System.out.println();
        grid.printGrid();
        System.out.printf("\nYou win! Time: %.2f seconds\n", time);
        System.out.println("Creating a new save entry...");
        Save.addEntry(username, modes.get(mode), String.format("%.2f", time));
    }
}
