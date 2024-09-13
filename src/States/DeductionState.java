package States;

import Rules.*;
import Utils.Difficulty;
import Utils.Grid;
import Utils.SudokuHandler;
import States.SolverStateFactory.StateType;
import Rules.DeductionRuleFactory.RuleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state where the solver attempts to solve the puzzle using deduction rules.
 */
public class DeductionState extends SolverState {

    private final List<DeductionRule> rules;

    /**
     * Constructs a DeductionState with the default set of deduction rules.
     *
     * @param sudokuHandler The SudokuHandler managing the solving process.
     */
    public DeductionState(SudokuHandler sudokuHandler) {
        super(sudokuHandler);
        this.rules = new ArrayList<>();
        rules.add(DeductionRuleFactory.getDeductionRule(RuleType.NAKED_SINGLE));
        rules.add(DeductionRuleFactory.getDeductionRule(RuleType.HIDDEN_SINGLE));
        rules.add(DeductionRuleFactory.getDeductionRule(RuleType.POINTING_PAIR));
    }

    /**
     * Attempts to apply deduction rules to solve the puzzle.
     *
     * @param grid The current Sudoku grid.
     * @return A StateResult indicating if any rules were applied and the highest difficulty encountered.
     */
    @Override
    public StateResult run(Grid grid) {
        Difficulty highestDifficulty = Difficulty.UNKNOWN;
        boolean ruleApplied = false;

        for (DeductionRule rule : rules) {
            if (rule.apply(grid)) {
                Difficulty ruleDifficulty = rule.getDifficulty();
                if (ruleDifficulty.compareTo(highestDifficulty) > 0) {
                    highestDifficulty = ruleDifficulty;
                }
                ruleApplied = true;
                break; // Stop after the first successful rule application
            }
        }

        if (!ruleApplied) {
            highestDifficulty = Difficulty.USER;
            // No rules could be applied; switch to UserState using the factory
            sudokuHandler.changeSolverState(StateType.USER);
        }

        return new StateResult(ruleApplied, highestDifficulty);
    }
}
