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

        // Ask the user if he wants to help the solver while solving the puzzle.
        boolean userHelp = IO.Input.askYesNo("Do you want to help the solver while solving the puzzle? (y/n)");

        // Ask the user for a file path to load a Sudoku puzzle from.
        String filePath = IO.Input.askFilePath("Enter the path to the Sudoku puzzle file:");
        if (filePath == null) {
            Logger.error("No file path provided. Exiting...");
            return;
        }

        SudokuFileProcessor processor = new SudokuFileProcessor();
        processor.processFile(filePath, userHelp);
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
