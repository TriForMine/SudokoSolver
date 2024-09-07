package Utils;

import java.util.Iterator;

public final class Utilities {
    /**
     * Returns the index of the box, given the row and column.
     *
     * @param row    0-8 (top to bottom)
     * @param column 0-8 (left to right)
     * @return index in the grid array
     */
    public static int getBoxIndex(int row, int column) {
        return (row / 3) * 3 + (column / 3);
    }

    public static int getBoxIndex(int i) {
        return getBoxIndex(getRowIndex(i), getColumnIndex(i));
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

    public static int[] getBoxIndices(int block) {
        int[] result = new int[9];
        int row = block / 3;
        int column = block % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i * 3 + j] = getIndex(row * 3 + i, column * 3 + j);
            }
        }
        return result;
    }

    /**
     * @param values list of values to check
     * @return true if all values are unique
     */
    public static boolean hasNoDuplicate(Iterator<Integer> values) {
        boolean[] seen = new boolean[9];
        while (values.hasNext()) {
            int value = values.next();
            if (value != -1) {
                if (seen[value - 1]) {
                    return false;
                }
                seen[value - 1] = true;
            }
        }
        return true;
    }
}
