package Rules;

import Utils.Difficulty;
import Utils.Grid;

public abstract class DeductionRule {
    private final Difficulty difficulty;

    protected DeductionRule(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean apply(Grid g) {
        boolean applied = false;

        for (int i = 0; i < 81; i++) {
            applied |= apply(g, i);
        }

        return applied;
    }

    public abstract boolean apply(Grid g, int i);
}
