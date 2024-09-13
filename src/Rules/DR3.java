package Rules;

import IO.Logger;
import Utils.Difficulty;
import Utils.Grid;

/**
 * DR3 (Deduction Rule 3) implements the Pointing Pair/Triple deduction.
 * If in a block, all candidates of a number are confined to a single row or column,
 * then that number cannot appear in the rest of that row or column outside the block.
 */
public class DR3 extends DeductionRule {

    private static final DR3 INSTANCE = new DR3();

    private DR3() {
        super(Difficulty.HARD);
    }

    public static DR3 getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean apply(Grid grid, int unused) {
        boolean hasChanged = false;

        // Iterate over all 3x3 blocks
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                // Check for pointing pairs/triples for each number 1-9
                for (int number = 1; number <= 9; number++) {
                    hasChanged |= applyPointingPair(grid, blockRow, blockCol, number);
                }
            }
        }

        return hasChanged;
    }

    private boolean applyPointingPair(Grid grid, int blockRow, int blockCol, int number) {
        boolean hasChanged = false;

        // Arrays to track which rows and columns within the block have 'number' as a possible value
        boolean[] rowsWithCandidate = new boolean[3];
        boolean[] colsWithCandidate = new boolean[3];

        // Analyze the block to find rows and columns containing the candidate
        for (int localRow = 0; localRow < 3; localRow++) {
            for (int localCol = 0; localCol < 3; localCol++) {
                int globalRow = blockRow * 3 + localRow;
                int globalCol = blockCol * 3 + localCol;
                if (grid.isPossibleValue(globalRow, globalCol, number)) {
                    rowsWithCandidate[localRow] = true;
                    colsWithCandidate[localCol] = true;
                }
            }
        }

        // Check if the candidate is confined to a single row within the block
        int confinedRow = getSingleTrueIndex(rowsWithCandidate);
        if (confinedRow != -1) {
            int globalRow = blockRow * 3 + confinedRow;
            hasChanged |= eliminateFromRowOutsideBlock(grid, globalRow, blockCol, number);
        }

        // Check if the candidate is confined to a single column within the block
        int confinedCol = getSingleTrueIndex(colsWithCandidate);
        if (confinedCol != -1) {
            int globalCol = blockCol * 3 + confinedCol;
            hasChanged |= eliminateFromColOutsideBlock(grid, blockRow, globalCol, number);
        }

        return hasChanged;
    }

    /**
     * Returns the index of the single true value in a boolean array, or -1 if zero or multiple trues.
     */
    private int getSingleTrueIndex(boolean[] array) {
        int count = 0;
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                count++;
                index = i;
                if (count > 1) {
                    return -1; // More than one true value
                }
            }
        }
        return (count == 1) ? index : -1;
    }

    private boolean eliminateFromRowOutsideBlock(Grid grid, int row, int blockCol, int number) {
        boolean removed = false;

        // Iterate over all columns in the row, excluding the block
        for (int col = 0; col < 9; col++) {
            if (col / 3 != blockCol && grid.isPossibleValue(row, col, number)) {
                grid.removePossibleValue(row, col, number);
                removed = true;
                Logger.trace("DR3: Removed %d from row %d, column %d", number, row, col);
            }
        }

        return removed;
    }

    private boolean eliminateFromColOutsideBlock(Grid grid, int blockRow, int col, int number) {
        boolean removed = false;

        // Iterate over all rows in the column, excluding the block
        for (int row = 0; row < 9; row++) {
            if (row / 3 != blockRow && grid.isPossibleValue(row, col, number)) {
                grid.removePossibleValue(row, col, number);
                removed = true;
                Logger.trace("DR3: Removed %d from column %d, row %d", number, col, row);
            }
        }

        return removed;
    }
}
