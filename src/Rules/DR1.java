package Rules;

import Utils.Grid;

/**
 * DR1 is a class that represents the first deduction rule.
 * The rule is as follows:
 * If a cell has only one possible value, then that value is the value of the cell.
 */
public class DR1 extends DeductionRule {
    private static final DR1 instance = new DR1();

    public static DR1 getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Grid g, int i) {
        if (g.getValue(i) != -1) {
            return false;
        }

        if (g.getPossibleValues(i).length == 1) {
            int value = g.getPossibleValues(i)[0];
            g.setValue(i, value);
            return true;
        }
        return false;
    }
}
