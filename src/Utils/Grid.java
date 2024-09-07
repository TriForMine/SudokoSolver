package Utils;

import java.util.*;
import java.util.function.Consumer;

import static Utils.Utilities.*;

public class Grid implements Iterable<Integer> {
    final private int[] grid = new int[81];
    final private BitSet[] possibleValues = new BitSet[81];


    public Grid() {
        for (int i = 0; i < 81; i++) {
            grid[i] = -1;
            possibleValues[i] = new BitSet(9);
            possibleValues[i].set(0, 9);
        }
    }

    public int getValue(int i) {
        return grid[i];
    }

    public void removePossibleValue(int i, int value) {
        possibleValues[i].clear(value - 1);
    }

    public void removePossibleValue(int row, int column, int value) {
        removePossibleValue(getIndex(row, column), value);
    }

    public boolean isPossibleValue(int i, int value) {
        return possibleValues[i].get(value - 1);
    }

    public boolean isPossibleValue(int row, int column, int value) {
        return isPossibleValue(getIndex(row, column), value);
    }

    public void setValue(int i, int value) {
        grid[i] = value;
        possibleValues[i].clear();

        for (int j = 0; j < 81; j++) {
            if (j == i) {
                continue;
            }
            if (getRowIndex(i) == getRowIndex(j) || getColumnIndex(i) == getColumnIndex(j) || getBoxIndex(i) == getBoxIndex(j)) {
                possibleValues[j].clear(value - 1);
            }
        }
    }

    /**
     * @param row 0-8 (top to bottom)
     * @return row
     */
    public Iterator<Integer> getRow(int row) {
        return Arrays.stream(grid, row * 9, row * 9 + 9).iterator();
    }

    /**
     * @param column 0-8 (left to right)
     * @return column
     */
    public Iterator<Integer> getColumn(int column) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            result.add(grid[i * 9 + column]);
        }
        return result.iterator();
    }

    /**
     * @param box 0-8 (left to right, top to bottom)
     * @return 3x3 box
     */
    public Iterator<Integer> getBox(int box) {
        List<Integer> result = new ArrayList<>();
        int row = box / 3;
        int column = box % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result.add(grid[(row * 3 + i) * 9 + column * 3 + j]);
            }
        }
        return result.iterator();
    }

    public int[] getPossibleValues(int i) {
        BitSet bitSet = possibleValues[i];
        int[] result = new int[bitSet.cardinality()];
        int index = 0;
        for (int j = bitSet.nextSetBit(0); j >= 0; j = bitSet.nextSetBit(j + 1)) {
            result[index++] = j + 1;
        }
        return result;
    }

    private boolean isRowValid(int row) {
        Iterator<Integer> values = getRow(row);
        return hasNoDuplicate(values);
    }

    private boolean isColumnValid(int column) {
        Iterator<Integer> values = getColumn(column);
        return hasNoDuplicate(values);
    }

    private boolean isBoxValid(int box) {
        Iterator<Integer> values = getBox(box);
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
        Iterator<Integer> iterator = iterator();
        for (int i = 0; i < 9; i++) {
            sb.append("| ");
            for (int j = 0; j < 9; j++) {
                if (iterator.hasNext()) {
                    int value = iterator.next();
                    sb.append(value == -1 ? " " : value);
                    sb.append(" ");
                }
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

    public void printPossibleValuesGrid() {
        StringBuilder sb = new StringBuilder();
        sb.append("Possible values:\n");
        String horizontalSeparator = "+-------------------------------+-------------------------------+-------------------------------+\n";
        sb.append(horizontalSeparator);
        for (int i = 0; i < 9; i++) {
            sb.append("| ");
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;

                int[] possibleValues = getPossibleValues(index);
                for (int k = 0; k < 9; k++) {
                    sb.append(possibleValues.length > k ? possibleValues[k] : " ");
                }
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
        System.out.println(sb.toString());
    }

    /**
     * @return An iterator over the elements in this grid
     */
    @Override
    public Iterator<Integer> iterator() {
        return Arrays.stream(grid).iterator();
    }

    /**
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int i = 0; i < 81; i++) {
            action.accept(grid[i]);
        }
    }
}
