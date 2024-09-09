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
        if (g.getValue(i) != -1) return false;

        int row = getRowIndex(i);
        int column = getColumnIndex(i);
        int block = getBoxIndex(row, column);
        int[] possibleValues = g.getPossibleValues(i);

        for (int value : possibleValues) {
            if (isHiddenSingleInRow(g, row, column, value) ||
                    isHiddenSingleInColumn(g, row, column, value) ||
                    isHiddenSingleInBlock(g, block, i, value)) {
                g.setValue(i, value);
                Logger.trace("DR2: Hidden Single at (%d, %d) with value %d", row, column, value);
                return true;
            }
        }
        return false;
    }

    private boolean isHiddenSingleInRow(Grid g, int row, int column, int value) {
        for (int j = 0; j < 9; j++) {
            if (j != column && g.isPossibleValue(getIndex(row, j), value)) {
                return false;
            }
        }
        return true;
    }

    private boolean isHiddenSingleInColumn(Grid g, int row, int column, int value) {
        for (int j = 0; j < 9; j++) {
            if (j != row && g.isPossibleValue(getIndex(j, column), value)) {
                return false;
            }
        }
        return true;
    }

    private boolean isHiddenSingleInBlock(Grid g, int block, int i, int value) {
        for (int index : getBoxIndices(block)) {
            if (index != i && g.isPossibleValue(index, value)) {
                return false;
            }
        }
        return true;
    }
}