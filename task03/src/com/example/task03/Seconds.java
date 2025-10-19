package com.example.task03;

/**
 * Интервал в секундах
 */
public class Seconds implements TimeUnit
{

    private final long amount;

    public Seconds(long amount) {
        this.amount = amount;
    }

    @Override
    public long toMillis() {
        return amount * 1000;
    }

    @Override
    public long toSeconds() {
        return amount;
    }

    @Override
    public long toMinutes() {return (amount + 30) / 60;}

    @Override
    public long getHours()
    {
        return (amount + 1800) / 3600;
    }
}
