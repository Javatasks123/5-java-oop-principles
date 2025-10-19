package com.example.task04;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler implements MessageHandler
{

    private final String filename;
    private PrintWriter writer;

    public FileHandler(String filename) throws IOException
    {
        this.filename = filename;
        this.writer = new PrintWriter(new FileWriter(filename, true), true);
    }

    @Override
    public void handle(String message)
    {
        if (writer != null) writer.println(message);
    }

    @Override
    public void close()
    {
        if (writer != null)
        {
            writer.close();
            writer = null;
        }
    }
}