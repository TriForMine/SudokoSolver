package Rules;

import IO.Logger;
import Utils.Difficulty;
import Utils.Grid;

/**
 * DR3 is a class that represents the third deduction rule.
 * The rule is as follows:
 * Pointing Pair / Triple
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
        for (int row = 0; row < 9; row++) {
            for (int num = 1; num < 10; num++) {
                int[] positions = new int[9];
                int count = 0;
                for (int col = 0; col < 9; col++) {
                    if (g.isPossibleValue(row, col, num)) {
                        positions[count++] = col;
                    }
                }
                if (count == 2 || count == 3) {
                    int blockRow = row / 3;
                    int blockCol = positions[0] / 3;
                    if (blockCol == positions[1] / 3 && (count == 2 || blockCol == positions[2] / 3) && removePossibleValuesInBlock(g, row, blockRow, blockCol, num)) {
                            Logger.trace("DR3: Pointing %s at (%d, %d) with value %d", count == 2 ? "Pair" : "Triple", row, blockCol, num);
                            return true;
                        }

                }
            }
        }
        return false;
    }

    private boolean removePossibleValuesInBlock(Grid g, int row, int blockRow, int blockCol, int num) {
        for (int r = blockRow * 3; r < blockRow * 3 + 3; r++) {
            if (r != row) {
                for (int c = blockCol * 3; c < blockCol * 3 + 3; c++) {
                    if (g.isPossibleValue(r, c, num)) {
                        g.removePossibleValue(r, c, num);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}