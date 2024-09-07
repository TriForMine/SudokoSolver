import Utils.SolverResult;
import Utils.SudokoHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static void runFile(String filename) {
        SudokoHandler sh = new SudokoHandler();

        int total = 0;
        int solved = 0;
        int impossible = 0;
        int easy = 0;
        int medium = 0;
        int hard = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
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

                SolverResult g = sh.solve();
                System.out.println(g.grid());

                total++;

                if (g.isSolved()) {
                    solved++;
                }

                switch (g.difficulty()) {
                    case EASY:
                        easy++;
                        break;
                    case MEDIUM:
                        medium++;
                        break;
                    case HARD:
                        hard++;
                        break;
                    case IMPOSSIBLE:
                        impossible++;
                        break;
                }

                line = reader.readLine();
            }

            System.out.printf("\u001B[32mSolved: %d/%d\u001B[0m%n", solved, total);
            System.out.printf("\u001B[34mEasy: %d\u001B[0m%n", easy);
            System.out.printf("\u001B[33mMedium: %d\u001B[0m%n", medium);
            System.out.printf("\u001B[31mHard: %d\u001B[0m%n", hard);
            System.out.printf("\u001B[35mImpossible: %d\u001B[0m%n", impossible);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        runFile("test.seed");
    }
}