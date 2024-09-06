import Rules.DR1;
import Rules.DeductionRule;
import Utils.Grid;
import Utils.SudokoHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SudokoHandler sh = new SudokoHandler();

        int solved = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
            String line = reader.readLine();
            while (line != null) {
                int[] values = new int[81];
                int index = 0;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == ',' || c == ' ') {
                        continue;
                    }
                    if (c == '.' || c == '0') {
                        values[index] = -1;
                    } else {
                        values[index] = Character.getNumericValue(c);
                    }
                    index++;
                }
                sh.initGrid(values);

                Grid g = sh.solve();
                System.out.println(g);

                if (g.isSolved()) {
                    solved++;
                }

                line = reader.readLine();
            }
            System.out.println("Solved " + solved + " out of 100");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}