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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SaveListProcessor {
    private SaveListProcessor() {} // Private constructor to prevent instantiation.

    private static void swapEntries(ArrayList<Entry> data, int x, int y) {
        Entry temp = data.get(x);
        data.set(x, data.get(y));
        data.set(y, temp);
    }

    public static void sortByNameAscending(ArrayList<Entry> data, int low, int high) {
        if (low < high) {
            int partitionIndex = namePartition(data, low, high);

            sortByNameAscending(data, low, partitionIndex - 1);
            sortByNameAscending(data, partitionIndex + 1, high);
        }
    }
    
    public static void sortByNameDescending(ArrayList<Entry> data, int low, int high) {
        sortByNameAscending(data, low, high);
        Collections.reverse(data);
    }

    private static int namePartition(ArrayList<Entry> data, int low, int high) {
        Entry pivot = data.get(high);
        String pivotName = pivot.getUsername();

        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            Entry currentEntry = data.get(j);
            String currentName = currentEntry.getUsername();

            if (currentName.compareTo(pivotName) < 0) {
                i++;
                swapEntries(data, i, j);
            }
        }

        swapEntries(data, i + 1, high);
        return i + 1;
    }

    public static void sortByTimeAscending(ArrayList<Entry> data, int low, int high) {
        if (low < high) {
            int partitionIndex = timePartition(data, low, high);

            sortByTimeAscending(data, low, partitionIndex - 1);
            sortByTimeAscending(data, partitionIndex + 1, high);
        }
    }

    public static void sortByTimeDescending(ArrayList<Entry> data, int low, int high) {
        sortByTimeAscending(data, low, high);
        Collections.reverse(data);
    }

    private static int timePartition(ArrayList<Entry> data, int low, int high) {
        Entry pivot = data.get(high);
        Double pivotTime = pivot.getTime();

        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            Entry currentEntry = data.get(j);
            Double time = currentEntry.getTime();

            if (time < pivotTime) {
                i++;
                swapEntries(data, i, j);
            }
        }

        swapEntries(data, i + 1, high);
        return i + 1;
    }

    public static void sortByModeAscending(ArrayList<Entry> data, int low, int high) {
        Map<String, Integer> modeMap = new HashMap<>();
        modeMap.put("Easy", 1);
        modeMap.put("Medium", 2);
        modeMap.put("Hard", 3);
        modeMap.put("Custom", 4);

        Collections.sort(data, (entry1, entry2) -> {
            Integer modeValue1 = modeMap.get(entry1.getMode());
            Integer modeValue2 = modeMap.get(entry2.getMode());

            return modeValue1.compareTo(modeValue2);
        });
    }

    public static void sortByModeDescending(ArrayList<Entry> data, int low, int high) {
        sortByModeAscending(data, low, high);
        Collections.reverse(data);
    }

    public static ArrayList<Entry> searchForUsername(ArrayList<Entry> data, String username) {
        ArrayList<Entry> results = new ArrayList<>();

        for (Entry entry : data) {
            if (entry.getUsername().equalsIgnoreCase(username)) {
                results.add(entry);
            }
        }

        return results;
    }
}