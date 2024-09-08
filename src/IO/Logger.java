package IO;

public class Logger {
    private static LogLevel logLevel = LogLevel.INFO;

    public static LogLevel getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    public static void log(LogLevel level, String message) {
        if (level.ordinal() >= logLevel.ordinal()) {
            String color = level.getColor();
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String[] lines = message.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                String timeToPrint = (i == 0) ? timestamp : " ";
                String colorToPrint = (i == 0) ? color : "";
                String levelToPrint = (i == 0) ? level.toString() : " ";
                System.out.printf("%-19s %s%-6s\u001B[0m: %s%n",
                        timeToPrint,
                        colorToPrint,
                        levelToPrint,
                        line);
            }
        }
    }

    public static void log(LogLevel level, Object message) {
        log(level, message.toString());
    }

    public static void trace(Object message) {
        log(LogLevel.TRACE, message);
    }

    public static void debug(Object message) {
        log(LogLevel.DEBUG, message);
    }

    public static void success(Object message) {
        log(LogLevel.SUCCESS, message);
    }

    public static void info(Object message) {
        log(LogLevel.INFO, message);
    }

    public static void warn(Object message) {
        log(LogLevel.WARN, message);
    }

    public static void error(Object message) {
        log(LogLevel.ERROR, message);
    }

    public static void log(LogLevel level, String message, Object... args) {
        log(level, String.format(message, args));
    }

    public static void trace(String message, Object... args) {
        log(LogLevel.TRACE, message, args);
    }

    public static void debug(String message, Object... args) {
        log(LogLevel.DEBUG, message, args);
    }

    public static void success(String message, Object... args) {
        log(LogLevel.SUCCESS, message, args);
    }

    public static void info(String message, Object... args) {
        log(LogLevel.INFO, message, args);
    }

    public static void warn(String message, Object... args) {
        log(LogLevel.WARN, message, args);
    }

    public static void error(String message, Object... args) {
        log(LogLevel.ERROR, message, args);
    }

    public static void log(LogLevel level, Exception e) {
        log(level, e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log(level, element.toString());
        }
    }

    public static void error(Exception e) {
        log(LogLevel.ERROR, e);
    }
}
