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
import java.util.Scanner;

public class Save {
    public static String fileName = "savedata.csv";
    private Save() {} // Private constructor to prevent instantiation

    // Reads the save file.
    public static void readFile() {
        File myFile = new File(fileName);

        try (Scanner scnr = new Scanner(myFile)) {
            System.out.println("Save file detected.");
            scnr.nextLine();
            while(scnr.hasNextLine()) {
                String line = scnr.nextLine();
                parseData(line);
            }
        }
        catch (FileNotFoundException e) { // If an error occurs, the program attempts to create a new save file.
            System.out.println("File not found. Creating new save file: ");
            createFile();
            readFile();
        }
    }

    public static void createFile() {
        try {
            File myFile = new File(fileName);
            
            // Attempts to access save file. If it isn't detected, it creates a new one.
            if (myFile.createNewFile()) {
                Path path = Paths.get(fileName);
                System.out.println("File created: " + myFile.getName());
                System.out.println("Created at: " + path.toAbsolutePath());
            } else {
                System.out.println("Save file detected.");
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        // Writes default text.
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("Name,Gamemode,Time");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public static void parseData(String line) {
        String data[] = line.split(",");

        try {
            System.out.println();
            System.out.println("Username: " + data[0]);
            System.out.println("Mode: " + data[1]);
            System.out.println("Time: " + data[2] + " seconds");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("An error occured while reading save file. It may be corrupted. If you see this message again, you may need to check savedata.csv by either deleting the entry or deleting the save file altogether.");
        }
    }
}
