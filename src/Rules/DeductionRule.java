package Rules;

import Utils.Difficulty;
import Utils.Grid;

/**
 * Abstract class representing a deduction rule for solving Sudoku puzzles.
 * Each specific deduction rule should extend this class and implement the abstract methods.
 */
public abstract class DeductionRule {

    private final Difficulty difficulty;

    /**
     * Constructor for DeductionRule.
     *
     * @param difficulty The difficulty level associated with this rule.
     */
    protected DeductionRule(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the difficulty level of the deduction rule.
     *
     * @return The difficulty level.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Applies the deduction rule to the entire grid.
     *
     * @param grid The Sudoku grid.
     * @return True if any changes were made to the grid; false otherwise.
     */
    public boolean apply(Grid grid) {
        boolean hasChanged = false;

        for (int cellIndex = 0; cellIndex < 81; cellIndex++) {
            if (apply(grid, cellIndex)) {
                hasChanged = true;
            }
        }

        return hasChanged;
    }

    /**
     * Applies the deduction rule to a specific cell in the grid.
     *
     * @param grid      The Sudoku grid.
     * @param cellIndex The index of the cell (0-80).
     * @return True if any changes were made to the grid; false otherwise.
     */
    public abstract boolean apply(Grid grid, int cellIndex);
}
