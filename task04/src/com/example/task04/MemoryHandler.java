package com.example.task04;

import java.util.ArrayList;
import java.util.List;

public class MemoryHandler implements MessageHandler
{

    private final MessageHandler otherHandler;
    private final int bufferSize;
    private final List<String> buffer;

    public int getBufferCount()
    {
        synchronized (buffer)
        {
            return buffer.size();
        }
    }

    public MemoryHandler(MessageHandler targetHandler, int bufferSize)
    {
        this.otherHandler = targetHandler;
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList<>(bufferSize);
    }

    public MemoryHandler(MessageHandler targetHandler)
    {
        this(targetHandler, 100);
    }

    @Override
    public void handle(String message)
    {
        synchronized (buffer)
        {
            buffer.add(message);
            if (buffer.size() >= bufferSize) flush();
        }
    }

    public void flush()
    {
        synchronized (buffer)
        {
            for (String message : buffer)  otherHandler.handle(message);
            buffer.clear();
        }
    }

    @Override
    public void close()
    {
        flush();
        otherHandler.close();
    }
}
