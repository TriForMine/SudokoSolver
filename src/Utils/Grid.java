package Utils;

import Iterators.GridBoxIterator;
import Iterators.GridColumnIterator;
import Iterators.GridIterator;
import Iterators.GridRowIterator;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.function.Consumer;

import static Utils.Utilities.*;

public class Grid implements Iterable<Integer> {
    private final int[] grid = new int[81];
    private final BitSet[] possibleValues = new BitSet[81];

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

    public int getValue(int row, int column) {
        return grid[getIndex(row, column)];
    }

    public void removePossibleValue(int i, int value) {
        possibleValues[i].clear(value - 1);
    }

    public void removePossibleValue(int row, int column, int value) {
        removePossibleValue(getIndex(row, column), value);
    }

    public boolean isCellEmpty(int i) {
        return grid[i] == -1;
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
    public GridIterator getRow(int row) {
        return new GridRowIterator(this, row);
    }

    /**
     * @param column 0-8 (left to right)
     * @return column
     */
    public GridIterator getColumn(int column) {
        return new GridColumnIterator(this, column);
    }

    /**
     * @param box 0-8 (left to right, top to bottom)
     * @return 3x3 box
     */
    public GridIterator getBox(int box) {
        return new GridBoxIterator(this, box);
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
        GridIterator values = getRow(row);
        return hasNoDuplicate(values);
    }

    private boolean isColumnValid(int column) {
        GridIterator values = getColumn(column);
        return hasNoDuplicate(values);
    }

    private boolean isBoxValid(int box) {
        GridIterator values = getBox(box);
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

    public String getPossibleValuesGrid() {
        StringBuilder sb = new StringBuilder("Possible values:\n");
        String horizontalSeparator = "+-------------------------------------------+-------------------------------------------+-------------------------------------------+\n";
        sb.append(horizontalSeparator);

        for (int i = 0; i < 9; i++) {
            sb.append("| ");
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                int value = getValue(index);

                if (value != -1) {
                    sb.append("\u001B[32m      ").append(value).append("       \u001B[0m");
                } else {
                    BitSet possibleValues = this.possibleValues[index];
                    sb.append("[");
                    for (int k = 0; k < 9; k++) {
                        if (possibleValues.get(k)) {
                            sb.append("\u001B[34m").append(k + 1).append("\u001B[0m");
                        } else {
                            sb.append(" ");
                        }
                        if ((k + 1) % 3 == 0 && k != 8) {
                            sb.append(" ");
                        }
                    }
                    sb.append("] ");
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
