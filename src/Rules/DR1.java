package Rules;

import Utils.Difficulty;
import Utils.Grid;

/**
 * DR1 is a class that represents the first deduction rule.
 * The rule is as follows:
 * Naked Single
 */
public class DR1 extends DeductionRule {
    private static final DR1 instance = new DR1();

    public DR1() {
        super(Difficulty.EASY);
    }

    public static DR1 getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Grid g, int i) {
        if (g.getValue(i) != -1) {
            return false;
        }

        int[] possibleValues = g.getPossibleValues(i);
        if (possibleValues.length == 1) {
            g.setValue(i, possibleValues[0]);
            return true;
        }

        return false;
    }
}
