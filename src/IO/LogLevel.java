package IO;

public enum LogLevel {
    TRACE("\u001B[37m"), // White
    DEBUG("\u001B[36m"), // Cyan
    INFO("\u001B[34m"),  // Blue
    SUCCESS("\u001B[32m"), // Green
    WARN("\u001B[33m"),  // Yellow
    ERROR("\u001B[31m"); // Red
    private final String color;

    LogLevel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
