package Utils;

import IO.Logger;
import Rules.DR1;
import Rules.DR2;
import Rules.DR3;
import Rules.DeductionRule;

import java.util.ArrayList;
import java.util.List;

public class SudokoHandler {
    final private List<DeductionRule> rules = new ArrayList<>();

    private Grid grid;

    public SudokoHandler() {
        grid = new Grid();
        rules.add(DR1.getInstance());
        rules.add(DR2.getInstance());
        rules.add(DR3.getInstance());
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

        while (!grid.isSolved() && iteration < 30) {
            boolean used = false;
            for (DeductionRule rule : rules) {
                if (rule.apply(grid)) {
                    if (rule.getDifficulty().ordinal() > difficulty.ordinal()) {
                        difficulty = rule.getDifficulty();
                    }
                    used = true;
                    break;
                }
            }

            Logger.trace("Iteration: %d Difficulty: %s\n%s", iteration, difficulty, grid.getPossibleValuesGrid());

            if (!used) {
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
