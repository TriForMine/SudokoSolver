import IO.LogLevel;
import IO.Logger;
import IO.MessageBuilder;
import Utils.SolverResult;
import Utils.SudokoHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static void runFile(String filename) {
        long startTime = System.currentTimeMillis();
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
                Logger.debug(g.grid());

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

            Logger.info(new MessageBuilder()
                    .info().text("Easy: %d\n")
                    .warn().text("Medium: %d\n")
                    .error().text("Hard: %d\n")
                    .color("\u001B[35m").text("Impossible: %d")
                    .args(easy, medium, hard, impossible)
                    .build());

            Logger.success("Successfully solved %d/%d sudokus in %dms (%.2fms per sudoku).", solved, total, System.currentTimeMillis() - startTime, (double) (System.currentTimeMillis() - startTime) / total);

            reader.close();
        } catch (IOException e) {
            Logger.error(e);
        }
    }

    public static void main(String[] args) {
        Logger.setLogLevel(LogLevel.INFO);

        Logger.info("Welcome to Sudoko Solver!");
        Logger.info("Loading sudokus from test.txt...");
        runFile("test.txt");
    }
}