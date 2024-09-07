package Rules;

import Utils.Difficulty;
import Utils.Grid;

/**
 * DR3 is a class that represents the third deduction rule.
 * The rule is as follows:
 * Pointing Pair
 */
public class DR3 extends DeductionRule {
    private static final DR3 instance = new DR3();

    public DR3() {
        super(Difficulty.HARD);
    }

    public static DR3 getInstance() {
        return instance;
    }

    @Override
    public boolean apply(Grid g, int i) {
        // Implement the pointing pair rule
        for (int row = 0; row < 9; row++) {
            for (int num = 1; num < 10; num++) {
                int[] positions = new int[9];
                int count = 0;
                for (int col = 0; col < 9; col++) {
                    if (g.isPossibleValue(row, col, num)) {
                        positions[count++] = col;
                    }
                }
                if (count == 2) {
                    int blockRow = row / 3;
                    int blockCol1 = positions[0] / 3;
                    int blockCol2 = positions[1] / 3;
                    if (blockCol1 == blockCol2) {
                        for (int r = blockRow * 3; r < blockRow * 3 + 3; r++) {
                            if (r != row) {
                                for (int c = blockCol1 * 3; c < blockCol1 * 3 + 3; c++) {
                                    if (g.isPossibleValue(r, c, num)) {
                                        g.removePossibleValue(r, c, num);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
