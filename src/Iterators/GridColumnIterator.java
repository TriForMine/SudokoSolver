package Iterators;

import Utils.Grid;

public class GridColumnIterator implements GridIterator {
    private final Grid grid;
    private final int column;
    private int index;

    public GridColumnIterator(Grid grid, int column) {
        this.grid = grid;
        this.column = column;
        this.index = 0;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean hasNext() {
        return index < 9;
    }

    /**
     * @return int
     */
    @Override
    public Integer next() {
        return grid.getValue(index++, column);
    }

}
