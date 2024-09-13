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

/**
 * Represents a Sudoku grid and provides methods for manipulating and querying the grid.
 * Each cell in the grid can hold a value from 1 to 9 or be empty (represented by -1).
 * The grid also maintains possible candidate values for each cell.
 */
public class Grid implements Iterable<Integer> {

    private final int[] cells = new int[81]; // Grid cells, -1 represents an empty cell
    private final BitSet[] possibleValues = new BitSet[81]; // Possible values for each cell

    /**
     * Initializes an empty Sudoku grid with all cells empty and all possible values (1-9) for each cell.
     */
    public Grid() {
        for (int cellIndex = 0; cellIndex < 81; cellIndex++) {
            cells[cellIndex] = -1; // Initialize cells as empty
            possibleValues[cellIndex] = new BitSet(9);
            possibleValues[cellIndex].set(0, 9); // All values (1-9) are possible initially
        }
    }

    /**
     * Gets the value of the cell at the specified index.
     *
     * @param cellIndex The cell index (0-80).
     * @return The value of the cell, or -1 if the cell is empty.
     */
    public int getValue(int cellIndex) {
        return cells[cellIndex];
    }

    /**
     * Gets the value of the cell at the specified row and column.
     *
     * @param row    The row index (0-8).
     * @param column The column index (0-8).
     * @return The value of the cell, or -1 if the cell is empty.
     */
    public int getValue(int row, int column) {
        return cells[getIndex(row, column)];
    }

    /**
     * Removes a possible candidate value from the cell at the specified index.
     *
     * @param cellIndex The cell index (0-80).
     * @param value     The candidate value to remove (1-9).
     */
    public void removePossibleValue(int cellIndex, int value) {
        possibleValues[cellIndex].clear(value - 1);
    }

    /**
     * Removes a possible candidate value from the cell at the specified row and column.
     *
     * @param row    The row index (0-8).
     * @param column The column index (0-8).
     * @param value  The candidate value to remove (1-9).
     */
    public void removePossibleValue(int row, int column, int value) {
        removePossibleValue(getIndex(row, column), value);
    }

    /**
     * Checks if the cell at the specified index is empty.
     *
     * @param cellIndex The cell index (0-80).
     * @return True if the cell is empty; false otherwise.
     */
    public boolean isCellEmpty(int cellIndex) {
        return cells[cellIndex] == -1;
    }

    /**
     * Checks if a candidate value is possible for the cell at the specified index.
     *
     * @param cellIndex The cell index (0-80).
     * @param value     The candidate value to check (1-9).
     * @return True if the value is a possible candidate; false otherwise.
     */
    public boolean isPossibleValue(int cellIndex, int value) {
        return possibleValues[cellIndex].get(value - 1);
    }

    /**
     * Checks if a candidate value is possible for the cell at the specified row and column.
     *
     * @param row    The row index (0-8).
     * @param column The column index (0-8).
     * @param value  The candidate value to check (1-9).
     * @return True if the value is a possible candidate; false otherwise.
     */
    public boolean isPossibleValue(int row, int column, int value) {
        return isPossibleValue(getIndex(row, column), value);
    }

    /**
     * Sets the value of the cell at the specified index and updates possible values accordingly.
     * It also removes the value from possible candidates in the same row, column, and box.
     *
     * @param cellIndex The cell index (0-80).
     * @param value     The value to set (1-9).
     */
    public void setValue(int cellIndex, int value) {
        cells[cellIndex] = value;
        possibleValues[cellIndex].clear(); // Clear possible values for this cell

        int cellRow = getRowIndex(cellIndex);
        int cellColumn = getColumnIndex(cellIndex);
        int cellBox = getBoxIndex(cellIndex);

        // Remove the value from possible candidates in the same row, column, and box
        for (int i = 0; i < 81; i++) {
            if (i == cellIndex) {
                continue;
            }
            int row = getRowIndex(i);
            int column = getColumnIndex(i);
            int box = getBoxIndex(i);

            if (row == cellRow || column == cellColumn || box == cellBox) {
                possibleValues[i].clear(value - 1);
            }
        }
    }

    /**
     * Returns an iterator over the values in the specified row.
     *
     * @param row The row index (0-8).
     * @return An iterator over the row's values.
     */
    public GridIterator getRow(int row) {
        return new GridRowIterator(this, row);
    }

    /**
     * Returns an iterator over the values in the specified column.
     *
     * @param column The column index (0-8).
     * @return An iterator over the column's values.
     */
    public GridIterator getColumn(int column) {
        return new GridColumnIterator(this, column);
    }

    /**
     * Returns an iterator over the values in the specified box (block).
     *
     * @param boxIndex The box index (0-8).
     * @return An iterator over the box's values.
     */
    public GridIterator getBox(int boxIndex) {
        return new GridBoxIterator(this, boxIndex);
    }

    /**
     * Gets all possible candidate values for the cell at the specified index.
     *
     * @param cellIndex The cell index (0-80).
     * @return An array of possible candidate values.
     */
    public int[] getPossibleValues(int cellIndex) {
        BitSet bitSet = possibleValues[cellIndex];
        int[] candidates = new int[bitSet.cardinality()];
        int index = 0;
        for (int bit = bitSet.nextSetBit(0); bit >= 0; bit = bitSet.nextSetBit(bit + 1)) {
            candidates[index++] = bit + 1;
        }
        return candidates;
    }

    /**
     * Checks if the specified row has no duplicate values.
     *
     * @param row The row index (0-8).
     * @return True if the row has no duplicates; false otherwise.
     */
    private boolean isRowValid(int row) {
        GridIterator values = getRow(row);
        return hasNoDuplicate(values);
    }

    /**
     * Checks if the specified column has no duplicate values.
     *
     * @param column The column index (0-8).
     * @return True if the column has no duplicates; false otherwise.
     */
    private boolean isColumnValid(int column) {
        GridIterator values = getColumn(column);
        return hasNoDuplicate(values);
    }

    /**
     * Checks if the specified box (block) has no duplicate values.
     *
     * @param boxIndex The box index (0-8).
     * @return True if the box has no duplicates; false otherwise.
     */
    private boolean isBoxValid(int boxIndex) {
        GridIterator values = getBox(boxIndex);
        return hasNoDuplicate(values);
    }

    /**
     * Checks if the entire grid is valid according to Sudoku rules (no duplicates in any row, column, or box).
     *
     * @return True if the grid is valid; false otherwise.
     */
    public boolean isValid() {
        for (int index = 0; index < 9; index++) {
            if (!isRowValid(index) || !isColumnValid(index) || !isBoxValid(index)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the Sudoku puzzle is solved (no empty cells and the grid is valid).
     *
     * @return True if the puzzle is solved; false otherwise.
     */
    public boolean isSolved() {
        for (int cellIndex = 0; cellIndex < 81; cellIndex++) {
            if (cells[cellIndex] == -1) {
                return false;
            }
        }
        return isValid();
    }

    /**
     * Returns a string representation of the grid, displaying the current values.
     *
     * @return A string representation of the grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String horizontalSeparator = "+-----------+-----------+-----------+\n";
        sb.append(horizontalSeparator);
        for (int row = 0; row < 9; row++) {
            sb.append("| ");
            for (int column = 0; column < 9; column++) {
                int cellIndex = getIndex(row, column);
                int value = cells[cellIndex];
                sb.append(value == -1 ? "   " : " " + value + " ");
                if ((column + 1) % 3 == 0) {
                    sb.append("| ");
                }
            }
            sb.append("\n");
            if ((row + 1) % 3 == 0) {
                sb.append(horizontalSeparator);
            }
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of the grid with row and column labels.
     *
     * @return A string representation of the grid.
     */
    public String toStringWithLabels() {
        StringBuilder sb = new StringBuilder();
        String horizontalSeparator = "  +-------+-------+-------+\n";
        sb.append("    1 2 3   4 5 6   7 8 9\n");
        sb.append(horizontalSeparator);
        for (int row = 0; row < 9; row++) {
            sb.append((char) ('A' + row)).append(" | ");
            for (int column = 0; column < 9; column++) {
                int cellIndex = getIndex(row, column);
                int value = cells[cellIndex];
                sb.append(value == -1 ? " " : value);
                sb.append(" ");
                if ((column + 1) % 3 == 0) {
                    sb.append("| ");
                }
            }
            sb.append("\n");
            if ((row + 1) % 3 == 0) {
                sb.append(horizontalSeparator);
            }
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of the grid, displaying possible candidate values for each cell.
     *
     * @return A string representation of the grid with possible values.
     */
    public String getPossibleValuesGrid() {
        StringBuilder sb = new StringBuilder("Possible values:\n");
        String horizontalSeparator = "+-------------------------------------------+-------------------------------------------+-------------------------------------------+\n";
        sb.append(horizontalSeparator);

        for (int row = 0; row < 9; row++) {
            sb.append("| ");
            for (int column = 0; column < 9; column++) {
                int cellIndex = getIndex(row, column);
                int value = getValue(cellIndex);

                if (value != -1) {
                    // Cell has a value, display it in green
                    sb.append("\u001B[32m      ").append(value).append("       \u001B[0m");
                } else {
                    // Cell is empty, display possible candidates in blue
                    BitSet cellPossibleValues = possibleValues[cellIndex];
                    sb.append("[");
                    for (int k = 0; k < 9; k++) {
                        if (cellPossibleValues.get(k)) {
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

                if ((column + 1) % 3 == 0) {
                    sb.append("| ");
                }
            }
            sb.append("\n");
            if ((row + 1) % 3 == 0) {
                sb.append(horizontalSeparator);
            }
        }
        return sb.toString();
    }

    /**
     * Returns an iterator over the cell values in the grid.
     *
     * @return An iterator over the grid's cell values.
     */
    @Override
    public Iterator<Integer> iterator() {
        return Arrays.stream(cells).iterator();
    }

    /**
     * Performs the given action for each cell value in the grid.
     *
     * @param action The action to be performed for each cell value.
     */
    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int cellValue : cells) {
            action.accept(cellValue);
        }
    }
}
