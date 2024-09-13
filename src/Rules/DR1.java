package Rules;

import IO.Logger;
import Utils.Difficulty;
import Utils.Grid;

/**
 * DR1 (Deduction Rule 1) implements the Naked Single deduction rule.
 * A Naked Single occurs when a cell has only one possible candidate number.
 * In such cases, the cell must be set to that candidate.
 */
public class DR1 extends DeductionRule {

    private static final DR1 INSTANCE = new DR1();

    private DR1() {
        super(Difficulty.EASY);
    }

    public static DR1 getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean apply(Grid grid, int cellIndex) {
        // If the cell already has a value, no deduction can be made.
        if (grid.getValue(cellIndex) != -1) {
            return false;
        }

        // Retrieve possible candidates for the cell.
        int[] possibleValues = grid.getPossibleValues(cellIndex);

        // If there's only one candidate, it's a Naked Single.
        if (possibleValues.length == 1) {
            int value = possibleValues[0];
            grid.setValue(cellIndex, value);

            int row = cellIndex / 9;
            int column = cellIndex % 9;
            Logger.trace("DR1: Naked Single at (%d, %d) with value %d", row, column, value);
            return true;
        }

        // No Naked Single found for this cell.
        return false;
    }
}
