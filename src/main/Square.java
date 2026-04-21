/*
 * Author: Aydan Romayor
 * Class: Square.java
 * 4/9/2026
 * 
 * Description:
 * This class controls the aspect of a single square on the game grid.
 * A square either has a value from 0-8 or is a bomb.
 * A square can either be opened or closed.
 * A square can be flagged or not.
 * If a square is closed, it always prints " - "
 * If a square is opened and is flagged, it always shows the flag graphic " P "
 * If a square is opened, empty, unflagged, and isn't a bomb, it prints " X "
 * If a sqaure is opened, isn't empty, unflagged, and isn't a bomb, it prints its numerical value " n "
 * If a square is opened, unflagged, and is a bomb, it shows the bomb graphic " ó "
 */

package main;

public class Square {
    // Fields
    private final static int EMPTY = 0;
    private final static int BOMB = 9;

    private int value;
    private boolean flagged;
    private boolean opened;

    // Text graphics
    private final static String OPEN_STRING = "X";
    private final static String CLOSED_STRING = "-";
    private final static String BOMB_STRING = "ó";
    private final static String FLAG_STRING = "P";

    // Constructor(s)
    public Square() {
        value = EMPTY;
        flagged = false;
        opened = false;
    }

    // methods
    public void setValue(int value) { this.value = value; }

    public void open() { opened = true; }

    public void flag() { flagged = !flagged; }

    public int getValue() { return value; }

    public boolean isOpen() { return opened; }

    public boolean isEmpty() { return value == EMPTY; }

    public boolean isFlagged() { return flagged; }

    public boolean isBomb() { return value == BOMB; }

    @Override
    public String toString() {
        if (!opened) return CLOSED_STRING;
        if (flagged) return FLAG_STRING;

        return switch (getValue()) {
            case EMPTY -> OPEN_STRING;
            case BOMB -> BOMB_STRING;
            default -> String.valueOf(getValue());
        };
    }
}