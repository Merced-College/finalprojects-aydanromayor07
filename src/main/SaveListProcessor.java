/*
 * Author: Aydan Romayor
 * Class: SaveListProcessor.java
 * 4/23/2026
 * 
 * Description:
 * This class's purpose is to allow the user to view sorted data and search for specific entries.
 */

package main;

import java.util.ArrayList;

public class SaveListProcessor {
    private SaveListProcessor() {} //Private constructor to prevent instantiation.

    private static void swapEntries(ArrayList<String> data, int x, int y) {
        String temp = data.get(x);
        data.set(x, data.get(y));
        data.set(y, temp);
    }

    public static void sortByName(ArrayList<String> data, int low, int high) {
        if (low < high) {
            int partitionIndex = namePartition(data, low, high);

            sortByName(data, low, partitionIndex - 1);
            sortByName(data, partitionIndex + 1, high);
        }
    }

    private static int namePartition(ArrayList<String> data, int low, int high) {
        String[] pivot = data.get(high).split(",");
        String pivotName = pivot[1];

        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            String[] line = data.get(j).split(",");
            String name = line[1];

            if (name.compareTo(pivotName) < 0) {
                i++;
                swapEntries(data, i, j);
            }
        }

        swapEntries(data, i + 1, high);
        return i + 1;
    }

    
}
