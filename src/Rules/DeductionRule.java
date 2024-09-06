package Rules;

import Utils.Grid;

public abstract class DeductionRule {
    public void apply(Grid g) {
        for (int i = 0; i < 81; i++) {
            apply(g, i);
        }
    }
    public abstract boolean apply(Grid g, int i);
}
