package Rules;

import IO.Logger;
import Utils.Difficulty;
import Utils.Grid;
import static Utils.Utilities.*;

/**
 * DR2 (Deduction Rule 2) implements the Hidden Single deduction rule.
 * A Hidden Single occurs when a candidate number can only go in one cell
 * within a unit (row, column, or block), even though that cell may have
 * multiple candidates.
 */
public class DR2 extends DeductionRule {

    private static final DR2 INSTANCE = new DR2();

    private DR2() {
        super(Difficulty.MEDIUM);
    }

    public static DR2 getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean apply(Grid grid, int cellIndex) {
        // If the cell already has a value, no deduction can be made.
        if (grid.getValue(cellIndex) != -1) {
            return false;
        }

        int row = getRowIndex(cellIndex);
        int column = getColumnIndex(cellIndex);
        int block = getBoxIndex(row, column);
        int[] possibleValues = grid.getPossibleValues(cellIndex);

        // Get the indices of the units (row, column, block) the cell belongs to.
        int[] rowIndices = getRowIndices(row);
        int[] columnIndices = getColumnIndices(column);
        int[] blockIndices = getBoxIndices(block);

        // Iterate over all possible candidates for this cell.
        for (int candidate : possibleValues) {
            if (isHiddenSingle(grid, rowIndices, cellIndex, candidate)) {
                grid.setValue(cellIndex, candidate);
                Logger.trace("DR2: Hidden Single in row at (%d, %d) with value %d", row, column, candidate);
                return true;
            } else if (isHiddenSingle(grid, columnIndices, cellIndex, candidate)) {
                grid.setValue(cellIndex, candidate);
                Logger.trace("DR2: Hidden Single in column at (%d, %d) with value %d", row, column, candidate);
                return true;
            } else if (isHiddenSingle(grid, blockIndices, cellIndex, candidate)) {
                grid.setValue(cellIndex, candidate);
                Logger.trace("DR2: Hidden Single in block at (%d, %d) with value %d", row, column, candidate);
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the candidate number is a hidden single within the specified unit.
     *
     * @param grid       The Sudoku grid.
     * @param unitIndices An array of cell indices representing the unit.
     * @param cellIndex   The index of the current cell.
     * @param candidate   The candidate number to check.
     * @return True if the candidate is a hidden single in the unit; otherwise, false.
     */
    private static boolean isHiddenSingle(Grid grid, int[] unitIndices, int cellIndex, int candidate) {
        for (int index : unitIndices) {
            if (index != cellIndex && grid.isPossibleValue(index, candidate)) {
                return false; // Candidate appears in another cell within the unit.
            }
        }
        return true; // Candidate is unique to this cell in the unit.
    }
}
