package States;

import Utils.SudokuHandler;

/**
 * Factory class for creating SolverState instances.
 */
public class SolverStateFactory {

    /**
     * Creates a SolverState instance based on the given state type.
     *
     * @param stateType     The type of state to create.
     * @param sudokuHandler The SudokuHandler managing the solving process.
     * @return A new instance of the requested SolverState.
     */
    public static SolverState createSolverState(StateType stateType, SudokuHandler sudokuHandler) {
        switch (stateType) {
            case DEDUCTION:
                return new DeductionState(sudokuHandler);
            case USER:
                return new UserState(sudokuHandler);
            default:
                throw new IllegalArgumentException("Invalid state type");
        }
    }

    /**
     * Enum representing the types of SolverStates available.
     */
    public enum StateType {
        DEDUCTION,
        USER
    }
}
