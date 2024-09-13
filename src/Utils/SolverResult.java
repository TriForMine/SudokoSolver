package Utils;

/**
 * Represents the result of the Sudoku solving process.
 *
 * @param grid      The final state of the Sudoku grid.
 * @param difficulty The highest difficulty level encountered during solving.
 * @param isSolved  Indicates whether the puzzle was successfully solved.
 */
public record SolverResult(Grid grid, Difficulty difficulty, boolean isSolved) {

    /**
     * Constructs a SolverResult with the specified grid and difficulty.
     * Determines if the grid is solved by calling grid.isSolved().
     *
     * @param grid       The final Sudoku grid.
     * @param difficulty The highest difficulty level encountered during solving.
     */
    public SolverResult(Grid grid, Difficulty difficulty) {
        this(grid, difficulty, grid.isSolved());
    }
}
