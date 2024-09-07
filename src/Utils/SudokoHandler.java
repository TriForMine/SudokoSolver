package Utils;

import Rules.DR1;
import Rules.DR2;
import Rules.DR3;
import Rules.DeductionRule;

public class SudokoHandler {
    DeductionRule dr1 = DR1.getInstance();
    DeductionRule dr2 = DR2.getInstance();
    DeductionRule dr3 = DR3.getInstance();

    private Grid grid;

    public SudokoHandler() {
        grid = new Grid();
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
        Difficulty difficulty = Difficulty.SOLVED;

        int iteration = 0;

        while (!grid.isSolved() && iteration < 100) {
            if (dr1.apply(grid)) {
                if (difficulty.ordinal() < Difficulty.EASY.ordinal()) {
                    difficulty = Difficulty.EASY;
                }
            } else if (dr2.apply(grid)) {
                if (difficulty.ordinal() < Difficulty.MEDIUM.ordinal()) {
                    difficulty = Difficulty.MEDIUM;
                }
            } else if (dr3.apply(grid)) {
                if (difficulty.ordinal() < Difficulty.HARD.ordinal()) {
                    difficulty = Difficulty.HARD;
                }
            } else {
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
