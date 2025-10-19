package com.example.task04;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

class RotationFileHandler implements MessageHandler
{

    private final String baseFilename;
    private final TemporalUnit rotationUnit;
    private PrintWriter writer;
    private LocalDateTime currentRotationTime;

    public RotationFileHandler(String baseFilename, TemporalUnit rotationUnit)
    {
        this.baseFilename = baseFilename;
        this.rotationUnit = rotationUnit;
        rotateIfNeeded();
    }

    public RotationFileHandler(String baseFilename)
    {
        this(baseFilename, ChronoUnit.HOURS);
    }

    @Override
    public void handle(String message)
    {
        rotateIfNeeded();
        if (writer != null) writer.println(message);
    }

    private void rotateIfNeeded()
    {
        LocalDateTime now = LocalDateTime.now();

        if (currentRotationTime == null || doRotate(now))
        {
            closeCurrentWriter();
            currentRotationTime = truncateTime(now);
            openNewWriter();
        }
    }

    private boolean doRotate(LocalDateTime now)
    {
        LocalDateTime nextRotation = currentRotationTime.plus(1, rotationUnit);
        return !now.isBefore(nextRotation);
    }

    private LocalDateTime truncateTime(LocalDateTime time)
    {
        if (rotationUnit == ChronoUnit.HOURS) return time.truncatedTo(ChronoUnit.HOURS);
        else if (rotationUnit == ChronoUnit.MINUTES) return time.truncatedTo(ChronoUnit.MINUTES);
        else if (rotationUnit == ChronoUnit.DAYS) return time.truncatedTo(ChronoUnit.DAYS);
        return time.truncatedTo(ChronoUnit.SECONDS);
    }

    private void closeCurrentWriter()
    {
        if (writer != null)
        {
            writer.close();
            writer = null;
        }
    }

    private void openNewWriter()
    {
        String filename = generateFilename();
        try { writer = new PrintWriter(new FileWriter(filename, true), true); }
        catch (IOException e) {throw new RuntimeException("Не удалось создать файл: " + filename, e);}
    }

    private String generateFilename()
    {
        String timestamp;
        if (rotationUnit == ChronoUnit.HOURS)
        {
            timestamp = currentRotationTime.toString().replace(":", "-").substring(0, 13);
        }
        else if (rotationUnit == ChronoUnit.MINUTES)
        {
            timestamp = currentRotationTime.toString().replace(":", "-").substring(0, 16);
        }
        else if (rotationUnit == ChronoUnit.DAYS)
        {
            timestamp = currentRotationTime.toString().substring(0, 10);
        }
        else
        {
            timestamp = currentRotationTime.toString().replace(":", "-");
        }
        return baseFilename + "_" + timestamp + ".log";
    }

    @Override
    public void close() {
        closeCurrentWriter();
    }
}