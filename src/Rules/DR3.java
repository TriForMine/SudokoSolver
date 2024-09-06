package Rules;

import Utils.Grid;

import static Utils.Utilities.*;

/**
 * DR3 is a class that represents the third deduction rule.
 * The rule is as follows:
 * If only a single number is missing in a box then that number must be in the cell.
 * We are trying to see if we can move an existing number from a cell to another cell.
 */
public class DR3 extends DeductionRule {
    private static final DR3 instance = new DR3();

    public static DR3 getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Grid g, int i) {
        return false;
    }
}
