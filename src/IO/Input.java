package IO;

public class Input {

        /**
        * Asks the user for a file path and returns it.
        *
        * @param message The message to display to the user.
        * @return The file path entered by the user.
        */
        public static String askFilePath(String message) {
            while (true) {
                System.out.println(message);
                String input = System.console().readLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                // Check if the file exists
                if (!new java.io.File(input).exists()) {
                    System.out.println("File not found. Please enter a valid file path.");
                    continue;
                }
                return input;
            }
        }

    /**
     * Asks the user a yes/no question and returns the answer.
     * The user can respond with "y" for yes or "n" for no.
     *
     * @param message The message to display to the user.
     *
     * @return True if the user answers "y", false if the user answers "n".
     */
    public static boolean askYesNo(String message) {
        while (true) {
            System.out.println(message);
            String input = System.console().readLine().trim().toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}
