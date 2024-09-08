package Iterators;

import Utils.Grid;

public class GridBoxIterator implements GridIterator {
    private final Grid grid;
    private final int box;
    private int index;

    public GridBoxIterator(Grid grid, int box) {
        this.grid = grid;
        this.box = box;
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
        int row = (box / 3) * 3 + index / 3;
        int column = (box % 3) * 3 + index % 3;
        index++;

        return grid.getValue(row, column);
    }
}
