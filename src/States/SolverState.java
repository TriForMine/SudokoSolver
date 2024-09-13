package States;

import Utils.Grid;
import Utils.SudokuHandler;

/**
 * Represents the state of the Sudoku solver.
 * Subclasses define specific behaviors for different solving strategies.
 */
public abstract class SolverState {

    protected SudokuHandler sudokuHandler;

    /**
     * Constructs a SolverState with a reference to the SudokuHandler.
     *
     * @param sudokuHandler The SudokuHandler managing the solving process.
     */
    public SolverState(SudokuHandler sudokuHandler) {
        this.sudokuHandler = sudokuHandler;
    }

    /**
     * Executes the solving strategy associated with this state.
     *
     * @param grid The current Sudoku grid.
     * @return A StateResult indicating if the state made progress and the difficulty involved.
     */
    public abstract StateResult run(Grid grid);
}
