# Sudoku Solver

A Java-based Sudoku Solver application that reads Sudoku puzzles from files, solves them using various deduction rules, and provides detailed logging of the solving process. This project implements multiple solving strategies and allows user interaction when the puzzle cannot be solved automatically.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Logging](#logging)
- [Deduction Rules](#deduction-rules)
- [Solver States](#solver-states)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Automatic Solving**: Implements multiple deduction rules to solve Sudoku puzzles automatically.
- **User Interaction**: Prompts the user for input when puzzles cannot be solved automatically.
- **Detailed Logging**: Provides logging at different levels (TRACE, DEBUG, INFO, SUCCESS, WARN, ERROR).
- **Difficulty Assessment**: Determines the difficulty level of puzzles based on the solving strategies used.
- **Puzzle Statistics**: Reports statistics such as total puzzles solved and difficulty distribution.
- **File Processing**: Reads puzzles from text files and processes them sequentially.
- **Flexible Design**: Uses design patterns like Factory and State for extensibility.

## Requirements

- Java Development Kit (JDK) 8 or higher
- A Java IDE or build tool like Maven or Gradle (optional)

## Project Structure

- `App/`: Contains the application facade and file processing classes.
    - `SudokuSolverApp.java`: Main application class.
    - `SudokuFileProcessor.java`: Handles reading puzzles from files.
- `IO/`: Input/output and logging utilities.
    - `Logger.java`: Handles logging with different levels and colors.
    - `LogLevel.java`: Enum for log levels.
    - `MessageBuilder.java`: Builds formatted log messages.
- `Rules/`: Deduction rules for solving puzzles.
    - `DeductionRule.java`: Abstract class for deduction rules.
    - `DeductionRuleFactory.java`: Factory for deduction rules.
    - `DR1.java`: Naked Single rule.
    - `DR2.java`: Hidden Single rule.
    - `DR3.java`: Pointing Pair/Triple rule.
- `States/`: State pattern implementation for solver states.
    - `SolverState.java`: Abstract state class.
    - `SolverStateFactory.java`: Factory for solver states.
    - `DeductionState.java`: Applies deduction rules.
    - `UserState.java`: Handles user input when automatic solving is not possible.
    - `StateResult.java`: Represents the result of a state execution.
- `Utils/`: Utility classes and data structures.
    - `Grid.java`: Represents the Sudoku grid.
    - `Utilities.java`: Helper methods for grid manipulation.
    - `Difficulty.java`: Enum representing puzzle difficulty levels.
    - `SolverResult.java`: Represents the result of the solving process.
    - `SudokuHandler.java`: Manages the solving process and state transitions.
- `Iterators/`: Custom iterators for grid traversal.
    - `GridIterator.java`: Interface for grid iterators.
    - `GridRowIterator.java`, `GridColumnIterator.java`, `GridBoxIterator.java`: Iterators over rows, columns, and boxes.
- `Main.java`: Entry point of the application.

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/SudokuSolver.git
   cd SudokuSolver
   ```

2. **Compile the project**:

    - Using the command line:

      ```bash
      javac Main.java
      ```

    - Or use an IDE like Eclipse or IntelliJ IDEA to import the project and build.

## Usage

1. **Prepare Puzzle Files**:

    - Create text files containing Sudoku puzzles.
    - Each line represents a puzzle.
    - Use `.` or `0` for empty cells.
    - You can also use ',' as a separator for easier reading.
    - Example of a puzzle line:

      ```
      ........1.....2.3...4.5........6.4...2.....7.3....8......9......46...5..8....3...
      ```
      
      or
      ```
      0,0,0,0,0,0,0,0,1,6,5,2,0,0,0,0,0,8,0,0,0,0,0,0,0,9,0,9,0,3,0,6,7,8,2,0,0,0,0,0,2,0,0,0,5,0,7,0,8,0,0,0,0,9,0,0,7,0,4,0,0,0,0,4,0,8,0,0,9,0,0,0,3,0,0,0,8,2,0,0,0
      ```
      
2. **Run the Application**:

    - Using the command line:

      ```bash
      java Main
      ```

    - The application will read puzzles from predefined files (e.g., `test.txt`, `big.sdm`).

3. **Interaction**:

    - If the solver cannot solve a puzzle automatically, it will prompt you for input.
    - Follow the prompts to provide values for specific cells.

## Logging

The application uses a custom `Logger` class for logging messages at different levels:

- **TRACE**: Detailed information typically of interest only when diagnosing problems.
- **DEBUG**: Information used for debugging.
- **INFO**: General information about the application's progress.
- **SUCCESS**: Indicates successful operations.
- **WARN**: Indicates a potential problem or important situation that should be noted.
- **ERROR**: Indicates a serious issue that has occurred.

Logs are colored in the console for better readability.

## Deduction Rules

The solver uses several deduction rules to solve the puzzles:

1. **DR1 - Naked Single**: If a cell has only one possible candidate, that value is assigned.
2. **DR2 - Hidden Single**: If a candidate number can only fit in one cell within a unit (row, column, or block), it's placed there.
3. **DR3 - Pointing Pair/Triple**: If candidates in a block are confined to a single row or column, they can be eliminated from the rest of that row or column outside the block.

## Solver States

The solver uses a state machine to manage the solving process:

- **DeductionState**: Applies deduction rules to solve the puzzle.
- **UserState**: Engages the user to provide input when automatic solving is not sufficient.

State transitions are managed by the `SolverStateFactory`.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with a detailed description of your changes.

## License

This project is licensed under the [MIT License](LICENSE).