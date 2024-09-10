package States;

import Utils.Difficulty;
import Utils.Grid;
import Utils.SudokuHandler;

import java.util.Scanner;

public class UserState extends SolverState {

    public UserState(SudokuHandler sudokuHandler) {
        super(sudokuHandler);
    }

    @Override
    public StateResult run(Grid grid) {
        System.out.println(grid.getPossibleValuesGrid());
        int cellIndex = promptUserForCellIndex(grid);
        int value = promptUserForValue(grid, cellIndex);
        grid.setValue(cellIndex, value);
        sudokuHandler.changeSolverState(new DeductionState(sudokuHandler));
        return new StateResult(true, Difficulty.USER);
    }

    private int promptUserForCellIndex(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        int cellIndex;
        System.out.print("Enter the cell index (0-80) where you want to place a value: ");
        while (true) {
            if (scanner.hasNextInt()) {
                cellIndex = scanner.nextInt();
                if (cellIndex >= 0 && cellIndex <= 80 && grid.isCellEmpty(cellIndex)) {
                    break;
                } else {
                    System.out.print("Invalid cell index or cell is not empty. Please enter a valid cell index (0-80): ");
                }
            } else {
                System.out.print("Invalid input. Please enter a number between 0 and 80: ");
                scanner.next(); // clear the invalid input
            }
        }
        return cellIndex;
    }

    private int promptUserForValue(Grid grid, int cellIndex) {
        Scanner scanner = new Scanner(System.in);
        int value;
        System.out.print("Enter a value (1-9) for cell " + cellIndex + ": ");
        while (true) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 1 && value <= 9 && grid.isPossibleValue(cellIndex, value)) {
                    break;
                } else {
                    System.out.print("Invalid value or not possible for this cell. Please enter a valid value (1-9): ");
                }
            } else {
                System.out.print("Invalid input. Please enter a number between 1 and 9: ");
                scanner.next(); // clear the invalid input
            }
        }
        return value;
    }
}
