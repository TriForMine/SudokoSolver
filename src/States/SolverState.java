package States;

import Utils.Grid;
import Utils.SudokuHandler;

// Use to represent the state of the solver
// Either solving by Rules or asking for a hint by the user
public abstract class SolverState {
    SudokuHandler sudokuHandler;

    public SolverState(SudokuHandler sudokuHandler) {
        this.sudokuHandler = sudokuHandler;
    }

    public abstract StateResult run(Grid grid);
}
