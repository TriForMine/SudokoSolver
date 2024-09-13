package States;

import Utils.Difficulty;
import Utils.Grid;
import Utils.SudokuHandler;
import States.SolverStateFactory.StateType;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the state where the solver requests input from the user to proceed.
 */
public class UserState extends SolverState {

    public UserState(SudokuHandler sudokuHandler) {
        super(sudokuHandler);
    }

    /**
     * Prompts the user to input a value for a cell to help solve the puzzle.
     *
     * @param grid The current Sudoku grid.
     * @return A StateResult indicating that user input was used.
     */
    @Override
    public StateResult run(Grid grid) {
        System.out.println(grid.toStringWithLabels());
        int cellIndex = promptUserForCellIndex(grid);
        int value = promptUserForValue(grid, cellIndex);
        grid.setValue(cellIndex, value);

        // Switch back to DeductionState using the factory after user input
        sudokuHandler.changeSolverState(StateType.DEDUCTION);

        return new StateResult(true, Difficulty.USER);
    }

    /**
     * Prompts the user to enter a valid cell position in standard notation (e.g., "A1").
     *
     * @param grid The current Sudoku grid.
     * @return The valid cell index entered by the user.
     */
    private int promptUserForCellIndex(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        int cellIndex;
        System.out.print("Enter the cell position (e.g., A1, B5, I9) where you want to place a value: ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            cellIndex = parseCellPosition(input);
            if (cellIndex != -1 && grid.isCellEmpty(cellIndex)) {
                break;
            } else {
                System.out.print("Invalid cell position or cell is not empty. Please enter a valid cell position (e.g., A1, B5, I9): ");
            }
        }
        return cellIndex;
    }

    /**
     * Parses the cell position from standard notation (e.g., "A1") to a cell index.
     *
     * @param input The cell position input by the user.
     * @return The cell index (0-80) or -1 if invalid.
     */
    private int parseCellPosition(String input) {
        Pattern pattern = Pattern.compile("^([A-I])(\\d)$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            char rowChar = matcher.group(1).charAt(0);
            int columnNum = Integer.parseInt(matcher.group(2));
            int row = rowChar - 'A';
            int column = columnNum - 1;
            if (row >= 0 && row < 9 && column >= 0 && column < 9) {
                return row * 9 + column;
            }
        }
        return -1;
    }

    /**
     * Prompts the user to enter a valid value for the specified cell.
     *
     * @param grid      The current Sudoku grid.
     * @param cellIndex The cell index where the user wants to place a value.
     * @return The valid value entered by the user.
     */
    private int promptUserForValue(Grid grid, int cellIndex) {
        Scanner scanner = new Scanner(System.in);
        int value;
        int row = cellIndex / 9;
        int column = cellIndex % 9;
        String cellPosition = String.format("%c%d", 'A' + row, column + 1);

        System.out.print("Enter a value (1-9) for cell " + cellPosition + ": ");
        while (true) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 1 && value <= 9 && grid.isPossibleValue(cellIndex, value)) {
                    break;
                } else {
                    System.out.print("Invalid value or not possible for this cell. Possible values are: ");
                    displayPossibleValues(grid, cellIndex);
                    System.out.print("\nPlease enter a valid value (1-9) for cell " + cellPosition + ": ");
                }
            } else {
                System.out.print("Invalid input. Please enter a number between 1 and 9 for cell " + cellPosition + ": ");
                scanner.next(); // Clear the invalid input
            }
        }
        return value;
    }

    /**
     * Displays the possible candidate values for the specified cell.
     *
     * @param grid      The current Sudoku grid.
     * @param cellIndex The cell index.
     */
    private void displayPossibleValues(Grid grid, int cellIndex) {
        int[] possibleValues = grid.getPossibleValues(cellIndex);
        for (int value : possibleValues) {
            System.out.print(value + " ");
        }
    }
}
