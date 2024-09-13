package Utils;

import Iterators.GridIterator;

/**
 * Utility class providing helper methods for Sudoku grid manipulation.
 */
public final class Utilities {

    // Private constructor to prevent instantiation
    private Utilities() {
    }

    /**
     * Returns the index of the box (block) given the row and column indices.
     *
     * @param row    Row index (0-8)
     * @param column Column index (0-8)
     * @return Box index (0-8)
     */
    public static int getBoxIndex(int row, int column) {
        return (row / 3) * 3 + (column / 3);
    }

    /**
     * Returns the index of the box (block) given the cell index.
     *
     * @param cellIndex Cell index (0-80)
     * @return Box index (0-8)
     */
    public static int getBoxIndex(int cellIndex) {
        int row = getRowIndex(cellIndex);
        int column = getColumnIndex(cellIndex);
        return getBoxIndex(row, column);
    }

    /**
     * Returns the column index given the cell index.
     *
     * @param cellIndex Cell index (0-80)
     * @return Column index (0-8)
     */
    public static int getColumnIndex(int cellIndex) {
        return cellIndex % 9;
    }

    /**
     * Returns the row index given the cell index.
     *
     * @param cellIndex Cell index (0-80)
     * @return Row index (0-8)
     */
    public static int getRowIndex(int cellIndex) {
        return cellIndex / 9;
    }

    /**
     * Returns the cell index given the row and column indices.
     *
     * @param row    Row index (0-8)
     * @param column Column index (0-8)
     * @return Cell index (0-80)
     */
    public static int getIndex(int row, int column) {
        return row * 9 + column;
    }

    /**
     * Returns an array of cell indices for the specified row.
     *
     * @param row Row index (0-8)
     * @return Array of cell indices in the row
     */
    public static int[] getRowIndices(int row) {
        int[] indices = new int[9];
        int startIndex = row * 9;
        for (int i = 0; i < 9; i++) {
            indices[i] = startIndex + i;
        }
        return indices;
    }

    /**
     * Returns an array of cell indices for the specified column.
     *
     * @param column Column index (0-8)
     * @return Array of cell indices in the column
     */
    public static int[] getColumnIndices(int column) {
        int[] indices = new int[9];
        for (int i = 0; i < 9; i++) {
            indices[i] = i * 9 + column;
        }
        return indices;
    }

    /**
     * Returns an array of cell indices for the specified box (block).
     *
     * @param boxIndex Box index (0-8)
     * @return Array of cell indices in the box
     */
    public static int[] getBoxIndices(int boxIndex) {
        int[] indices = new int[9];
        int startRow = (boxIndex / 3) * 3;
        int startColumn = (boxIndex % 3) * 3;
        int count = 0;
        for (int row = startRow; row < startRow + 3; row++) {
            for (int column = startColumn; column < startColumn + 3; column++) {
                indices[count++] = getIndex(row, column);
            }
        }
        return indices;
    }

    /**
     * Checks if the provided sequence of values contains no duplicates (excluding -1).
     *
     * @param values Iterator over the values to check
     * @return True if all values are unique (excluding -1); false otherwise
     */
    public static boolean hasNoDuplicate(GridIterator values) {
        boolean[] seen = new boolean[9]; // indices 0-8 correspond to numbers 1-9
        while (values.hasNext()) {
            int value = values.next();
            if (value != -1) {
                if (seen[value - 1]) {
                    return false; // Duplicate found
                }
                seen[value - 1] = true;
            }
        }
        return true;
    }
}
