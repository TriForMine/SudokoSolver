package Utils;

import IO.Logger;
import Rules.DR1;
import Rules.DR2;
import Rules.DR3;
import Rules.DeductionRule;
import States.DeductionState;
import States.SolverState;
import States.StateResult;

import java.util.ArrayList;
import java.util.List;

public class SudokuHandler {
    private SolverState solverState;
    private Grid grid;

    public SudokuHandler() {
        grid = new Grid();
        solverState = new DeductionState(this);
    }

    public void changeSolverState(SolverState solverState) {
        this.solverState = solverState;
    }

    public void initGrid(int[] values) {
        grid = new Grid();

        for (int i = 0; i < 81; i++) {
            if (values[i] != -1) {
                grid.setValue(i, values[i]);
            }
        }
    }

    public SolverResult solve() {
        Difficulty difficulty = Difficulty.UNKNOWN;

        int iteration = 0;

        while (!grid.isSolved() && iteration < 30) {
            Logger.trace("Iteration: %d Difficulty: %s\n%s", iteration, difficulty, grid.getPossibleValuesGrid());

            StateResult result = solverState.run(grid);

            if (result.difficulty().ordinal() > difficulty.ordinal()) {
                difficulty = result.difficulty();
            }

            if (!result.used()) {
                difficulty = Difficulty.IMPOSSIBLE;
                break;
            }

            iteration++;
        }

        return new SolverResult(grid, difficulty);
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
