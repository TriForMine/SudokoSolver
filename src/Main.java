import IO.LogLevel;
import IO.Logger;
import IO.MessageBuilder;
import Utils.SolverResult;
import Utils.SudokuHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static void runFile(String filename) {
        long startTime = System.currentTimeMillis();
        SudokuHandler sh = new SudokuHandler();

        int total = 0, solved = 0, impossible = 0, easy = 0, medium = 0, hard = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int[] values = parseLine(line);
                sh.initGrid(values);

                SolverResult g = sh.solve();
                Logger.debug(g.grid());

                total++;
                if (g.isSolved()) solved++;

                switch (g.difficulty()) {
                    case EASY -> easy++;
                    case MEDIUM -> medium++;
                    case HARD -> hard++;
                    case IMPOSSIBLE -> impossible++;
                    default -> Logger.error("Unknown difficulty: %s", g.difficulty());
                }
            }

            Logger.info(new MessageBuilder()
                    .info().text("Easy: %d\n")
                    .warn().text("Medium: %d\n")
                    .error().text("Hard: %d\n")
                    .color("\u001B[35m").text("Impossible: %d")
                    .args(easy, medium, hard, impossible)
                    .build());

            long elapsedTime = System.currentTimeMillis() - startTime;
            Logger.success("Successfully solved %d/%d sudokus in %dms (%.2fms per sudoku).", solved, total, elapsedTime, (double) elapsedTime / total);

        } catch (IOException e) {
            Logger.error(e);
        }
    }

    private static int[] parseLine(String line) {
        int[] values = new int[81];
        int index = 0;
        for (char c : line.toCharArray()) {
            if (c == ',' || c == ' ') continue;
            values[index++] = (c == '.' || c == '0') ? -1 : Character.getNumericValue(c);
        }
        return values;
    }

    public static void main(String[] args) {
        Logger.setLogLevel(LogLevel.INFO);

        Logger.info("Welcome to Sudoku Solver!");
        Logger.info("Loading sudokus from test.txt...");
        runFile("test.txt");
    }
}