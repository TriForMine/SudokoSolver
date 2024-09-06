package Utils;

public final class Utilities {
    /**
     * Returns the index of the box, given the row and column.
     * @param row   0-8 (top to bottom)
     * @param column 0-8 (left to right)
     * @return index in the grid array
     */
    public static int getBoxIndex(int row, int column) {
        return (row / 3) * 3 + (column / 3);
    }

    public static int getColumnIndex(int i) {
        return i % 9;
    }

    public static int getRowIndex(int i) {
        return i / 9;
    }

    public static int getIndex(int row, int column) {
        return row * 9 + column;
    }

    /**
     * @param values list of values to check
     * @return true if all values are unique
     */
    public static boolean hasNoDuplicate(int[] values) {
        boolean[] seen = new boolean[9];
        for (int value : values) {
            if (value == -1) {
                continue;
            }
            if (seen[value - 1]) {
                return false;
            }
            seen[value - 1] = true;
        }
        return true;
    }
}
