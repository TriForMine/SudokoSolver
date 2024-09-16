package App;

import IO.Logger;
import IO.MessageBuilder;
import Utils.Difficulty;
import Utils.SolverResult;
import Utils.SudokuHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Processes Sudoku puzzles from a file and manages the solving process.
 */
public class SudokuFileProcessor {
    private int totalPuzzles;
    private int solvedPuzzles;
    private int impossiblePuzzles;
    private int easyPuzzles;
    private int mediumPuzzles;
    private int hardPuzzles;
    private int userPuzzles;

    /**
     * Processes a file containing Sudoku puzzles.
     *
     * @param filename The name of the file to process.
     */
    public void processFile(String filename) {
        Logger.info("Loading Sudoku puzzles from %s...", filename);
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            SudokuHandler sudokuHandler = new SudokuHandler();

            while ((line = reader.readLine()) != null) {
                int[] gridValues = parseLine(line);
                sudokuHandler.initGrid(gridValues);

                SolverResult result = sudokuHandler.solve();
                Logger.debug(result.grid());

                totalPuzzles++;
                if (result.isSolved()) {
                    solvedPuzzles++;
                }

                categorizePuzzle(result);
            }

            displayStatistics();
            logExecutionTime(startTime);

        } catch (IOException e) {
            Logger.error("Failed to process file %s", filename);
            Logger.error(e);
        }
    }

    /**
     * Parses a line from the input file into a Sudoku grid.
     *
     * @param line The line to parse.
     * @return An array representing the Sudoku grid.
     */
    private int[] parseLine(String line) {
        int[] values = new int[81];
        int index = 0;
        for (char c : line.toCharArray()) {
            if (isDelimiter(c)) {
                continue;
            }
            values[index++] = isEmptyCell(c) ? -1 : Character.getNumericValue(c);
            if (index >= 81) {
                break;
            }
        }
        return values;
    }

    /**
     * Categorizes the puzzle based on its difficulty level.
     *
     * @param result The result of solving the puzzle.
     */
    private void categorizePuzzle(SolverResult result) {
        switch (result.difficulty()) {
            case EASY -> easyPuzzles++;
            case MEDIUM -> mediumPuzzles++;
            case HARD -> hardPuzzles++;
            case IMPOSSIBLE -> impossiblePuzzles++;
            case USER -> userPuzzles++;
            default -> Logger.warn("Unknown difficulty level: %s", result.difficulty());
        }
    }

    /**
     * Displays statistics about the puzzles processed.
     */
    private void displayStatistics() {
        String statisticsMessage = new MessageBuilder()
                .info().text("Easy: %d\n")
                .warn().text("Medium: %d\n")
                .error().text("Hard: %d\n")
                .color("\u001B[35m").text("Impossible: %d\n")
                .text("User Hint: %d")
                .args(easyPuzzles, mediumPuzzles, hardPuzzles, impossiblePuzzles, userPuzzles)
                .build();

        Logger.info(statisticsMessage);
    }

    /**
     * Logs the execution time of the file processing.
     *
     * @param startTime The start time in milliseconds.
     */
    private void logExecutionTime(long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        double timePerPuzzle = (double) elapsedTime / totalPuzzles;

        Logger.success("Successfully solved %d/%d puzzles in %d ms (%.2f ms per puzzle).",
                solvedPuzzles, totalPuzzles, elapsedTime, timePerPuzzle);
    }

    /**
     * Checks if the character is a delimiter.
     *
     * @param c The character to check.
     * @return True if the character is a delimiter, false otherwise.
     */
    private boolean isDelimiter(char c) {
        return c == ',' || c == ' ';
    }

    /**
     * Checks if the cell is empty based on the character.
     *
     * @param c The character to check.
     * @return True if the cell is empty, false otherwise.
     */
    private boolean isEmptyCell(char c) {
        return c == '.' || c == '0';
    }
}
