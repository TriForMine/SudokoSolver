package Rules;

import Utils.Grid;

import static Utils.Utilities.*;

/**
 * This class is a representation of the DR2 deduction rule.
 * The rule is as follows:
 * Naked Pair: If two cells in a row or a column contain the same two numbers, then those two numbers can be removed from the other cells in the row or column.
 */
public class DR2 extends DeductionRule {
    private static final DR2 instance = new DR2();

    public static DR2 getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Grid g, int i) {
        return false;
    }
}
