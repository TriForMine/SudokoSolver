package Utils;

import IO.Logger;
import States.SolverStateFactory;
import States.SolverState;
import States.StateResult;
import States.SolverStateFactory.StateType;

/**
 * Handles the Sudoku solving process by managing the grid and solver states.
 */
public class SudokuHandler {

    private SolverState solverState;
    private Grid grid;
    public boolean userHelp;

    /**
     * Initializes the SudokuHandler with a new grid and sets the initial solver state using the factory.
     */
    public SudokuHandler(boolean userHelp) {
        this.grid = new Grid();
        changeSolverState(StateType.DEDUCTION);
        this.userHelp = userHelp;
    }

    /**
     * Changes the current solver state using the factory.
     *
     * @param stateType The type of the new solver state to switch to.
     */
    public void changeSolverState(StateType stateType) {
        this.solverState = SolverStateFactory.createSolverState(stateType, this);
    }

    /**
     * Initializes the grid with the given values.
     * Cells with a value of -1 are considered empty.
     *
     * @param values An array of 81 integers representing the Sudoku grid.
     */
    public void initGrid(int[] values) {
        grid = new Grid();
        for (int cellIndex = 0; cellIndex < 81; cellIndex++) {
            if (values[cellIndex] != -1) {
                grid.setValue(cellIndex, values[cellIndex]);
            }
        }
    }

    /**
     * Solves the Sudoku puzzle using the current solver state.
     *
     * @return A SolverResult containing the final grid and the determined difficulty level.
     */
    public SolverResult solve() {
        Difficulty difficulty = Difficulty.UNKNOWN;
        int iteration = 0;

        // Limit iterations to prevent infinite loops in case of unsolvable puzzles.
        while (!grid.isSolved() && iteration < 1000) {
            Logger.trace("Iteration: %d Difficulty: %s\n%s", iteration, difficulty, grid.getPossibleValuesGrid());

            StateResult result = solverState.run(grid);

            // Update the highest difficulty encountered.
            if (result.difficulty().ordinal() > difficulty.ordinal()) {
                difficulty = result.difficulty();
            }

            if (!result.used()) {
                break;
            }
        }

        // Determine if the puzzle is solved or impossible.
        if (!grid.isSolved()) {
            difficulty = Difficulty.IMPOSSIBLE;
        }

        return new SolverResult(grid, difficulty);
    }

    /**
     * Returns the string representation of the current grid.
     *
     * @return The grid as a formatted string.
     */
    @Override
    public String toString() {
        return grid.toString();
    }
}
