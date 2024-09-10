package States;

import Rules.DR1;
import Rules.DR2;
import Rules.DR3;
import Rules.DeductionRule;
import Utils.Difficulty;
import Utils.Grid;
import Utils.SudokuHandler;

import java.util.ArrayList;
import java.util.List;

public class DeductionState extends SolverState {
    private final List<DeductionRule> rules = new ArrayList<>();

    public DeductionState(SudokuHandler sudokuHandler) {
        super(sudokuHandler);
        rules.add(DR1.getInstance());
        rules.add(DR2.getInstance());
        rules.add(DR3.getInstance());
    }

    @Override
    public StateResult run(Grid grid) {
        Difficulty difficulty = Difficulty.UNKNOWN;
        boolean used = false;
        for (DeductionRule rule : rules) {
            if (rule.apply(grid)) {
                difficulty = rule.getDifficulty();
                used = true;
                break;
            }
        }

        if (!used) {
            sudokuHandler.changeSolverState(new UserState(sudokuHandler));
        }

        return new StateResult(used, difficulty);
    }
}
