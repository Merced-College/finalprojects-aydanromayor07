/*
 * Author: Aydan Romayor
 * Class: Entry.java
 * 4/24/2026
 * 
 * Description:
 * This class represents a single entry in the save file.
 * It contains the username, gamemode, and time of a single game.
 * Automatically converts a CSV string to an Entry object and vice versa.
 */

package main;

public class Entry {
    private final String csvLiteral;

    private final int entryNumber;
    private final String username;
    private final String mode;
    private final double time;


    public Entry(String csvLiteral) {
        this.csvLiteral = csvLiteral;

        String[] split = csvLiteral.split(",");

        entryNumber = Integer.parseInt(split[0]);
        username = split[1];
        mode = split[2];
        time = Double.parseDouble(split[3]);
    }

    public int getEntryNumber() { return entryNumber; }
    public String getUsername() { return username; }
    public String getMode() { return mode; }
    public double getTime() { return time; }

    @Override
    public String toString(){
        return csvLiteral;
    }
}
