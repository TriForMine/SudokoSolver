package Rules;

import IO.Logger;
import Utils.Difficulty;
import Utils.Grid;

import static Utils.Utilities.*;

/**
 * This class is a representation of the DR2 deduction rule.
 * The rule is as follows:
 * Hidden Single
 */
public class DR2 extends DeductionRule {
    private static final DR2 instance = new DR2();

    public DR2() {
        super(Difficulty.MEDIUM);
    }

    public static DR2 getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Grid g, int i) {

        if (g.getValue(i) != -1) {
            return false;
        }

        int row = getRowIndex(i);
        int column = getColumnIndex(i);
        int block = getBoxIndex(row, column);

        int[] possibleValues = g.getPossibleValues(i);

        for (int value : possibleValues) {
            boolean isHiddenSingle = true;

            // Check row
            for (int j = 0; j < 9; j++) {
                if (j == column) {
                    continue;
                }
                int index = getIndex(row, j);
                if (g.getPossibleValues(index).length > 0 && g.isPossibleValue(index, value)) {
                    isHiddenSingle = false;
                    break;
                }
            }

            if (isHiddenSingle) {
                g.setValue(i, value);
                return true;
            }

            isHiddenSingle = true;

            // Check column
            for (int j = 0; j < 9; j++) {
                if (j == row) {
                    continue;
                }
                int index = getIndex(j, column);
                if (g.getPossibleValues(index).length > 0 && g.isPossibleValue(index, value)) {
                    isHiddenSingle = false;
                    break;
                }
            }

            if (isHiddenSingle) {
                g.setValue(i, value);
                Logger.trace("DR2: Hidden Single at (%d, %d) with value %d", row, column, value);
                return true;
            }

            isHiddenSingle = true;

            // Check block
            int[] blockIndices = getBoxIndices(block);
            for (int index : blockIndices) {
                if (index == i) {
                    continue;
                }
                if (g.getPossibleValues(index).length > 0 && g.isPossibleValue(index, value)) {
                    isHiddenSingle = false;
                    break;
                }
            }

            if (isHiddenSingle) {
                g.setValue(i, value);
                Logger.trace("DR2: Hidden Single at (%d, %d) with value %d", row, column, value);
                return true;
            }
        }

        return false;
    }
}
