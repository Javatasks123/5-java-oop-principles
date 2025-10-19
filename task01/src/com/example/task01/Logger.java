package com.example.task01;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Logger
{
    private static final Map<String, Logger> loggers = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final String name;
    private Level level = Level.INFO;

    public String getName()
    {
        return name;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }
    public Level getLevel()
    {
        return level;
    }
    private Logger(String name)
    {
        this.name = name;
    }

    public static Logger getLogger(String name)
    {
        return loggers.computeIfAbsent(name, Logger::new);
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

        public int getPriority()
        {
            return priority;
        }
    }

    public void debug(String message)
    {
        log(Level.DEBUG, message);
    }

    public void debug(String pattern, Object... args)
    {
        log(Level.DEBUG, pattern, args);
    }

    public void info(String message)
    {
        log(Level.INFO, message);
    }

    public void info(String pattern, Object... args)
    {
        log(Level.INFO, pattern, args);
    }

    public void warning(String message)
    {
        log(Level.WARNING, message);
    }

    public void warning(String pattern, Object... args)
    {
        log(Level.WARNING, pattern, args);
    }

    public void error(String message)
    {
        log(Level.ERROR, message);
    }

    public void error(String pattern, Object... args)
    {
        log(Level.ERROR, pattern, args);
    }

    public void log(Level messageLevel, String message)
    {
        if (messageLevel.getPriority() >= level.getPriority())
        {
            LocalDateTime now = LocalDateTime.now();
            String formattedMessage = String.format("[%s] %s %s %s - %s",
                    messageLevel,
                    now.format(DATE_FORMATTER),
                    now.format(TIME_FORMATTER),
                    name,
                    message);
            System.out.println(formattedMessage);
        }
    }

    public void log(Level messageLevel, String pattern, Object... args)
    {
        if (messageLevel.getPriority() >= level.getPriority())
            log(messageLevel, String.format(pattern, args));
    }
}
