package IO;

/**
 * Logger class for handling logging at different levels.
 * Supports various log levels and formats messages with timestamps and colors.
 */
public class Logger {

    private static LogLevel logLevel = LogLevel.INFO;

    // Private constructor to prevent instantiation
    private Logger() {
    }

    /**
     * Gets the current log level.
     *
     * @return The current LogLevel.
     */
    public static LogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the log level. Messages below this level will not be logged.
     *
     * @param level The LogLevel to set.
     */
    public static void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    /**
     * Logs a message at the specified log level.
     *
     * @param level   The LogLevel at which to log.
     * @param message The message to log.
     */
    public static void log(LogLevel level, String message) {
        if (level.ordinal() >= logLevel.ordinal()) {
            String color = level.getColor();
            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String[] lines = message.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                String timeToPrint = (i == 0) ? timestamp : "                   "; // Align timestamps
                String colorToPrint = (i == 0) ? color : "";
                String levelToPrint = (i == 0) ? level.toString() : "      ";
                System.out.printf("%s %s%-6s\u001B[0m: %s%n",
                        timeToPrint,
                        colorToPrint,
                        levelToPrint,
                        line);
            }
        }
    }

    /**
     * Logs an object's string representation at the specified log level.
     *
     * @param level   The LogLevel at which to log.
     * @param message The object whose string representation will be logged.
     */
    public static void log(LogLevel level, Object message) {
        log(level, message.toString());
    }

    /**
     * Logs a message at TRACE level.
     *
     * @param message The message to log.
     */
    public static void trace(Object message) {
        log(LogLevel.TRACE, message);
    }

    /**
     * Logs a message at DEBUG level.
     *
     * @param message The message to log.
     */
    public static void debug(Object message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * Logs a message at SUCCESS level.
     *
     * @param message The message to log.
     */
    public static void success(Object message) {
        log(LogLevel.SUCCESS, message);
    }

    /**
     * Logs a message at INFO level.
     *
     * @param message The message to log.
     */
    public static void info(Object message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Logs a message at WARN level.
     *
     * @param message The message to log.
     */
    public static void warn(Object message) {
        log(LogLevel.WARN, message);
    }

    /**
     * Logs a message at ERROR level.
     *
     * @param message The message to log.
     */
    public static void error(Object message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Logs a formatted message at the specified log level.
     *
     * @param level   The LogLevel at which to log.
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers in the format string.
     */
    public static void log(LogLevel level, String message, Object... args) {
        log(level, String.format(message, args));
    }

    /**
     * Logs a formatted message at TRACE level.
     *
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers.
     */
    public static void trace(String message, Object... args) {
        log(LogLevel.TRACE, message, args);
    }

    /**
     * Logs a formatted message at DEBUG level.
     *
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers.
     */
    public static void debug(String message, Object... args) {
        log(LogLevel.DEBUG, message, args);
    }

    /**
     * Logs a formatted message at SUCCESS level.
     *
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers.
     */
    public static void success(String message, Object... args) {
        log(LogLevel.SUCCESS, message, args);
    }

    /**
     * Logs a formatted message at INFO level.
     *
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers.
     */
    public static void info(String message, Object... args) {
        log(LogLevel.INFO, message, args);
    }

    /**
     * Logs a formatted message at WARN level.
     *
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers.
     */
    public static void warn(String message, Object... args) {
        log(LogLevel.WARN, message, args);
    }

    /**
     * Logs a formatted message at ERROR level.
     *
     * @param message The format string.
     * @param args    Arguments referenced by the format specifiers.
     */
    public static void error(String message, Object... args) {
        log(LogLevel.ERROR, message, args);
    }

    /**
     * Logs an exception's message and stack trace at the specified log level.
     *
     * @param level The LogLevel at which to log.
     * @param e     The exception to log.
     */
    public static void log(LogLevel level, Exception e) {
        log(level, e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            log(level, "    at " + element.toString());
        }
    }

    /**
     * Logs an exception's message and stack trace at ERROR level.
     *
     * @param e The exception to log.
     */
    public static void error(Exception e) {
        log(LogLevel.ERROR, e);
    }
}
