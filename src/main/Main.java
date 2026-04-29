/*
 * Author: Aydan Romayor
 * Class: Main.java
 * 4/24/2026
 * 
 * Description:
 * This is the Main java file for the Minesweeper game.
 * To run the game, run this file.
 */

package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scnr = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to...");
        System.out.println(" __       __  __                                                                                               \r\n" + //
                        "|  \\     /  \\|  \\                                                                                              \r\n" + //
                        "| $$\\   /  $$ \\$$ _______    ______    _______  __   __   __   ______    ______    ______    ______    ______  \r\n" + //
                        "| $$$\\ /  $$$|  \\|       \\  /      \\  /       \\|  \\ |  \\ |  \\ /      \\  /      \\  /      \\  /      \\  /      \\ \r\n" + //
                        "| $$$$\\  $$$$| $$| $$$$$$$\\|  $$$$$$\\|  $$$$$$$| $$ | $$ | $$|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\\r\n" + //
                        "| $$\\$$ $$ $$| $$| $$  | $$| $$    $$ \\$$    \\ | $$ | $$ | $$| $$    $$| $$    $$| $$  | $$| $$    $$| $$   \\$$\r\n" + //
                        "| $$ \\$$$| $$| $$| $$  | $$| $$$$$$$$ _\\$$$$$$\\| $$_/ $$_/ $$| $$$$$$$$| $$$$$$$$| $$__/ $$| $$$$$$$$| $$      \r\n" + //
                        "| $$  \\$ | $$| $$| $$  | $$ \\$$     \\|       $$ \\$$   $$   $$ \\$$     \\ \\$$     \\| $$    $$ \\$$     \\| $$      \r\n" + //
                        " \\$$      \\$$ \\$$ \\$$   \\$$  \\$$$$$$$ \\$$$$$$$   \\$$$$$\\$$$$   \\$$$$$$$  \\$$$$$$$| $$$$$$$   \\$$$$$$$ \\$$      \r\n" + //
                        "                                                                                 | $$                          \r\n" + //
                        "                                                                                 | $$                          \r\n" + //
                        "                                                                                  \\$$                          ");

        System.out.println();
        while (true) {
            System.out.println();
            System.out.println("Main Menu:");
            System.out.println("> 1: Play Game");
            System.out.println("> 2: View Leaderboard");
            System.out.println("> 3: Options");
            System.out.println("> 4: Exit");
            int userChoice = userInput(1,4);

            switch (userChoice) {
                case 1 -> playGame();
                case 2 -> saveMenu();
                case 3 -> options();
                case 4 -> System.out.println("Exiting...");
                default -> {
                }
            }

            if (userChoice == 4) break;
        }
    }

    // Helper function that allows the user to input their values without problem.
    private static int userInput(int min, int max) {
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

    public static void playGame() {
        System.out.print("\nEnter your username.\n> ");
        scnr.nextLine();
        String username = scnr.nextLine();

        Game game = new Game(username);
        game.runGame();
    }

    public static void saveMenu() {
        while (true) {
            System.out.println("\nSave Menu Options: ");
            System.out.println("> 1: View Full Leaderboard");
            System.out.println("> 2: Sort Leaderboard");
            System.out.println("> 3: Back to Main Menu");

            int choice = userInput(1, 3);

            ArrayList<String> data = Save.readFile();
            ArrayList<Entry> entries = Save.convertToEntryList(data);
            switch (choice) {
                case 1 -> {
                    if (entries.isEmpty()) {
                        System.out.println("\nNo entries found.");
                        break;
                    }

                    System.out.printf("\n%d entries found.\n", entries.size());
                    System.out.println("How many entries to display? (Enter -1 to display all entries)");
                    int n = userInput(-1, entries.size());

                    System.out.println();
                    if (n == -1) { Save.printSaveList(entries);}
                    else {
                        ArrayList<Entry> sublist = new ArrayList<>(entries.subList(0, n));
                        Save.printSaveList(sublist);
                    }

                    break;
                }
                case 2 -> {
                    sortMenu(entries);
                }
                case 3 -> {
                    System.out.println("Returning to Main Menu...");
                    return;
                }
                default -> {
                }
            }
        }
    }

    public static void sortMenu(ArrayList<Entry> entries) {
        if (entries.isEmpty()) {
            System.out.println("\nNo entries found.");
            return;
        }

        while (true) {

            System.out.println("\nSort Options:");
            System.out.println("> 1: Sort by Name");
            System.out.println("> 2: Sort by Time");
            System.out.println("> 3: Sort by Mode");
            System.out.println("> 4: Search for Username");
            System.out.println("> 5: Print Leaderboard");
            System.out.println("> 6: Back to Save Menu");

            int choice = userInput(1, 6); 

            switch (choice) {
                case 1 -> {
                    System.out.println("\nChoose sort mode: ");
                    System.out.println("> 1: Ascending");
                    System.out.println("> 2: Descending");

                    int sortChoice = userInput(1, 2);
                    if (sortChoice == 1) {
                        SaveListProcessor.sortByNameAscending(entries, 0, entries.size() - 1);
                    } else if (sortChoice == 2) {
                        SaveListProcessor.sortByNameDescending(entries, 0, entries.size() - 1);
                    }
                    break;
                }
                case 2 -> {
                    System.out.println("\nChoose sort mode: ");
                    System.out.println("> 1: Ascending");
                    System.out.println("> 2: Descending");
                    int sortChoice = userInput(1, 2);
                    if (sortChoice == 1) {
                        SaveListProcessor.sortByTimeAscending(entries, 0, entries.size() - 1);
                    } else if (sortChoice == 2) {
                        SaveListProcessor.sortByTimeDescending(entries, 0, entries.size() - 1);
                    }
                    break;
                }
                case 3 -> {
                    System.out.println("\nChoose sort mode: ");
                    System.out.println("> 1: Ascending");
                    System.out.println("> 2: Descending");
                    int sortChoice = userInput(1, 2);
                    if (sortChoice == 1) {
                        SaveListProcessor.sortByModeAscending(entries, 0, entries.size() - 1);
                    } else if (sortChoice == 2) {
                        SaveListProcessor.sortByModeDescending(entries, 0, entries.size() - 1);
                    }
                    break;
                }
                case 4 -> {
                    System.out.println("\nEnter the username to search for:\n> ");
                    scnr.nextLine();
                    String username = scnr.nextLine();

                    ArrayList<Entry> searchResults = SaveListProcessor.searchForUsername(entries, username);
                    if (searchResults.isEmpty()) {
                        System.out.println("\nNo entries found for username: " + username);
                    } else {
                        System.out.printf("\n%d entries found for username: %s\n", searchResults.size(), username);
                        entries = searchResults;
                    }
                    break;
                }
                case 5 -> {
                    System.out.printf("\n%d entries found.\n", entries.size());
                    System.out.println("How many entries to display? (Enter -1 to display all entries)");
                    int n = userInput(-1, entries.size());

                    System.out.println();
                    if (n == -1) { Save.printSaveList(entries);}
                    else {
                        ArrayList<Entry> sublist = new ArrayList<>(entries.subList(0, n));
                        Save.printSaveList(sublist);
                    }
                    break;
                }
                case 6 -> {
                    System.out.println("Returning to Save Menu...");
                    return;
                }
            }
        }
    }

    public static void options() {
        System.out.println("\nOptions:");
        System.out.println("> 1: Reset Leaderboard");
        System.out.println("> 2: Back to Main Menu");

        int choice = userInput(1, 2);
        if (choice == 1) {
            Save.resetFile();
            System.out.println("\nLeaderboard reset.");
        }
    }
}