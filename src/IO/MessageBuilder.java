package IO;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {
    private final List<String> parts;
    private final List<String> colors;
    private Object[] args;

    public MessageBuilder() {
        this.parts = new ArrayList<>();
        this.colors = new ArrayList<>();
    }

    public MessageBuilder color(String color) {
        colors.add(color);
        return this;
    }

    public MessageBuilder text(String text) {
        parts.add(text);
        if (colors.size() < parts.size()) {
            colors.add(null); // Placeholder for no color
        }
        return this;
    }

    public MessageBuilder args(Object... args) {
        this.args = args;
        return this;
    }

    public String build() {
        StringBuilder formattedMessage = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            String part = parts.get(i);
            String color = colors.get(i);
            if (color != null) {
                formattedMessage.append(color);
            }
            formattedMessage.append(part);
            if (color != null) {
                formattedMessage.append("\u001B[0m");
            }
        }
        return String.format(formattedMessage.toString(), args);
    }

    @Override
    public String toString() {
        return build();
    }

    public MessageBuilder trace() {
        return color(LogLevel.TRACE.getColor());
    }

    public MessageBuilder debug() {
        return color(LogLevel.DEBUG.getColor());
    }

    public MessageBuilder info() {
        return color(LogLevel.INFO.getColor());
    }

    public MessageBuilder success() {
        return color(LogLevel.SUCCESS.getColor());
    }

    public MessageBuilder warn() {
        return color(LogLevel.WARN.getColor());
    }

    public MessageBuilder error() {
        return color(LogLevel.ERROR.getColor());
    }
}