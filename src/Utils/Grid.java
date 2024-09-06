package Utils;

import static Utils.Utilities.*;

public class Grid {
    final private int[] grid = new int[81];
    final private boolean[] fixed = new boolean[81];

    public Grid() {
        for (int i = 0; i < 81; i++) {
            grid[i] = -1;
        }
    }

    public Grid(int[] grid) {
        System.arraycopy(grid, 0, this.grid, 0, 81);
    }

    public int getValue(int i) {
        return grid[i];
    }

    public boolean isFixed(int i) {
        return fixed[i];
    }

    public void setValue(int i, int value) {
        grid[i] = value;
    }

    public void setValue(int i, int value, boolean isFixed) {
        grid[i] = value;
        fixed[i] = isFixed;
    }

    /**
     * @param row 0-8 (top to bottom)
     * @return row
     */
    public int[] getRow(int row) {
        int[] result = new int[9];
        System.arraycopy(grid, row * 9, result, 0, 9);
        return result;
    }

    /**
     * @param column 0-8 (left to right)
     * @return column
     */
    public int[] getColumn(int column) {
        int[] result = new int[9];
        for (int i = 0; i < 9; i++) {
            result[i] = grid[i * 9 + column];
        }
        return result;
    }

    /**
     * @param box 0-8 (left to right, top to bottom)
     * @return 3x3 box
     */
    public int[] getBox(int box) {
        int[] result = new int[9];
        int row = box / 3;
        int column = box % 3;
        for (int i = 0; i < 3; i++) {
            System.arraycopy(grid, (row * 3 + i) * 9 + column * 3, result, i * 3, 3);
        }
        return result;
    }

    public int[] getPossibleValues(int i) {
        if (grid[i] != -1 || fixed[i]) {
            return new int[0];
        }

        int row = getRowIndex(i);
        int column = getColumnIndex(i);
        int box = getBoxIndex(row, column);

        int[] rowValues = getRow(row);
        int[] columnValues = getColumn(column);
        int[] boxValues = getBox(box);

        boolean[] seen = new boolean[9];
        for (int value : rowValues) {
            if (value != -1) {
                seen[value - 1] = true;
            }
        }

        for (int value : columnValues) {
            if (value != -1) {
                seen[value - 1] = true;
            }
        }

        for (int value : boxValues) {
            if (value != -1) {
                seen[value - 1] = true;
            }
        }

        int count = 0;
        for (boolean b : seen) {
            if (!b) {
                count++;
            }
        }

        int[] result = new int[count];
        int index = 0;
        for (int j = 0; j < 9; j++) {
            if (!seen[j]) {
                result[index] = j + 1;
                index++;
            }
        }

        return result;
    }

    private boolean isRowValid(int row) {
        int[] values = getRow(row);
        return hasNoDuplicate(values);
    }

    private boolean isColumnValid(int column) {
        int[] values = getColumn(column);
        return hasNoDuplicate(values);
    }

    private boolean isBoxValid(int box) {
        int[] values = getBox(box);
        return hasNoDuplicate(values);
    }

    public boolean isValid() {
        for (int i = 0; i < 9; i++) {
            if (!isRowValid(i) || !isColumnValid(i) || !isBoxValid(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSolved() {
        for (int i = 0; i < 81; i++) {
            if (grid[i] == -1) {
                return false;
            }
        }

        return isValid();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String horizontalSeparator = "+-------+-------+-------+\n";
        sb.append(horizontalSeparator);
        for (int i = 0; i < 9; i++) {
            sb.append("| ");
            for (int j = 0; j < 9; j++) {
                int value = grid[i * 9 + j];
                sb.append(value == -1 ? " " : value);
                sb.append(" ");
                if ((j + 1) % 3 == 0) {
                    sb.append("| ");
                }
            }
            sb.append("\n");
            if ((i + 1) % 3 == 0) {
                sb.append(horizontalSeparator);
            }
        }
        return sb.toString();
    }
}
