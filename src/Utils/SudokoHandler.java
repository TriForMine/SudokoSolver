package Utils;

import Rules.DR1;
import Rules.DR2;
import Rules.DR3;
import Rules.DeductionRule;

public class SudokoHandler {
    DeductionRule dr1 = DR1.getInstance();
    DeductionRule dr2 = DR2.getInstance();
    DeductionRule dr3 = DR3.getInstance();

    private Grid grid;

    public SudokoHandler() {
        grid = new Grid();
    }

    public void initGrid(int[] values) {
        grid = new Grid();
        for (int i = 0; i < 81; i++) {
            if (values[i] != -1) {
                grid.setValue(i, values[i], true);
            }
        }
    }

    public Grid solve() {


        boolean wasDR1Applied = false;
        boolean wasDR2Applied = false;
        boolean wasDR3Applied = false;

        int iteration = 0;

        while (!grid.isSolved() && iteration < 30) {
                if (iteration < 10) {
                    dr1.apply(grid);
                    wasDR1Applied = true;
                } else if (iteration < 20) {
                    dr2.apply(grid);
                    wasDR2Applied = true;
                } else {
                    dr3.apply(grid);
                    wasDR3Applied = true;
                }
            iteration++;
        }

        System.out.println("iteration: " + iteration);
        System.out.printf("Difficulty: %s\n", wasDR1Applied ? "Easy" : wasDR2Applied ? "Medium" : "Hard");

        return grid;
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
