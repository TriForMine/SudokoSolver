package Rules;

import Utils.Grid;

public abstract class DeductionRule {
    public boolean apply(Grid g) {
        boolean applied = false;

        for (int i = 0; i < 81; i++) {
            applied |= apply(g, i);
        }

        return applied;
    }
    public abstract boolean apply(Grid g, int i);
}
