package temp;

import java.util.ArrayList;
import main.Save;

public class SaveTest {
    public static void main(String[] args) {
        ArrayList<String> data = Save.readFile();

        System.out.println();
        Save.printSaveList(data);

        Save.resetFile();
        
        data = Save.readFile();
        System.out.println();
        Save.printSaveList(data);

        Save.addEntry("Test4", "Hard", String.valueOf(294.43));

        data = Save.readFile();
        System.out.println();
        Save.printSaveList(data);
    }
}
