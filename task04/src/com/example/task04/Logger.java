package com.example.task04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String name;
    private final List<MessageHandler> handlers;
    private Level level;

    public Logger(String name)
    {
        this.name = name;
        this.handlers = new ArrayList<>();
        this.level = Level.DEBUG;
    }

    public enum Level
    {
        DEBUG(0),
        INFO(1),
        WARNING(2),
        ERROR(3);

        private final int priority;

        Level(int priority)
        {
            this.priority = priority;
        }

        public boolean isEnabled(Level minLevel)
        {
            return this.priority >= minLevel.priority;
        }
    }

    public void addHandler(MessageHandler handler)
    {
        handlers.add(handler);
    }

    public void removeHandler(MessageHandler handler)
    {
        handlers.remove(handler);
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public void log(Level level, String message)
    {
        if (level.isEnabled(this.level))
        {
            String formattedMessage = formatMessage(level, message);
            for (MessageHandler handler : handlers) handler.handle(formattedMessage);
        }
    }

    public void debug(String message)
    {
        log(Level.DEBUG, message);
    }

    public void info(String message)
    {
        log(Level.INFO, message);
    }

    public void warning(String message)
    {
        log(Level.WARNING, message);
    }

    public void error(String message)
    {
        log(Level.ERROR, message);
    }

    private String formatMessage(Level level, String message)
    {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        return String.format("[%s] [%s] [%s] %s", timestamp, name, level, message);
    }

    public void close()
    {
        for (MessageHandler handler : handlers) handler.close();
        handlers.clear();
    }
}