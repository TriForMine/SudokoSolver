package App;

import IO.Logger;
import IO.LogLevel;

/**
 * Facade class for the Sudoku Solver application.
 * Manages the overall process of solving Sudoku puzzles from files.
 */
public class SudokuSolverApp {

    /**
     * Starts the Sudoku Solver application.
     */
    public void start() {
        Logger.setLogLevel(LogLevel.INFO);
        Logger.info("Welcome to Sudoku Solver!");

        SudokuFileProcessor processor = new SudokuFileProcessor();
        processor.processFile("test.txt");
        processor.processFile("big.sdm");
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new SudokuSolverApp().start();
    }
}
