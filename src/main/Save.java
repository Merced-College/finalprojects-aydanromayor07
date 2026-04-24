/*
 * Author: Aydan Romayor
 * Class: Save.java
 * 4/22/2026
 * 
 * Description:
 * This class controls storing and accessing sava data.
 */

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Save {
    public static String fileName = "savedata.csv";
    private Save() {} // Private constructor to prevent instantiation.

    // Reads the save file.
    public static ArrayList<String> readFile() {
        File saveFile = new File(fileName);
        ArrayList<String> data = new ArrayList<>();

        // Attempts to access the save file.
        try (Scanner scnr = new Scanner(saveFile)) {
            System.out.println("Save file detected.\n");
            scnr.nextLine();

            int currentEntry = 0;
            while(scnr.hasNextLine()) {
                currentEntry++; // For better user readability, the entry will start at number 1.
                String line = scnr.nextLine();
                String[] splitLine = line.split(",");

                // Check to make sure the data in each entry is there (name, mode, time).
                // If not, skip entry.
                try {
                    String x = splitLine[0];
                    String y = splitLine[1];
                    String z = splitLine[2];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error with processing entry #" + currentEntry + ". Skipping entry.");
                    continue;
                }

                // If no issues with parsing entry, add it to the list. Also append the entry number to the front.
                data.add(currentEntry + "," + line);
            }
            
            scnr.close();
        }
        catch (FileNotFoundException e) { // If an error occurs, the program attempts to create a new save file.
            System.out.println("File not found. Creating new save file: ");
            createFile();
            readFile();
        }

        return data;
    }

    // Creates a new save file.
    public static void createFile() {
        try {
            File saveFile = new File(fileName);
            
            // Attempts to access save file. If it isn't detected, it creates a new one.
            if (saveFile.createNewFile()) {
                Path path = Paths.get(fileName);
                System.out.println("File created: " + saveFile.getName());
                System.out.println("Created at: " + path.toAbsolutePath());
            } else {
                System.out.println("Save file detected.");
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        // Writes default text.
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.flush();
            writer.write("Name,Gamemode,Time");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    // Reset the save file. Delete the save file and recreate it.
    public static void resetFile() {
        File saveFile = new File(fileName);
        
        if(saveFile.delete()) {
            System.out.println("\nResetting the save file.\n");
            createFile();
        } else {
            System.out.println("\nFailed to reset the file.");
        }
    }

    public static void addEntry(String name, String mode, double time) {
        try (FileWriter writer = new FileWriter(fileName, true) ){
            writer.flush();
            writer.write("\n" + name + "," + mode + "," + time);
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to write entry to the save file.");
        }
    }

    // Takes a list of entries, parses the data, and prints it.
    public static void printSaveList(ArrayList<String> data) {
        for (String line : data) {
            String[] splitLine = line.split(",");

            String entryNumber = splitLine[0];
            String name = splitLine[1];
            String mode = splitLine[2];
            String time = splitLine[3];

            System.out.println("Entry #" + entryNumber + ": Name: " + name + ", Mode: " + mode + ", Time: " + time + " seconds");
        }
    }
}