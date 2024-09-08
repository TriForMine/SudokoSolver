package Iterators;

import Utils.Grid;

public class GridRowIterator implements GridIterator {
    private final Grid grid;
    private final int line;
    private int index;

    public GridRowIterator(Grid grid, int line) {
        this.grid = grid;
        this.line = line;
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
        return grid.getValue(line, index++);
    }

}
