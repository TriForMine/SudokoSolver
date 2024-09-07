package Utils;

public record SolverResult(Grid grid, Difficulty difficulty, boolean isSolved) {
    public SolverResult(Grid grid, Difficulty difficulty) {
        this(grid, difficulty, grid.isSolved());
    }
}