package com.example.task03;

/**
 * Интервал в миллисекундах
 */
public class Milliseconds implements TimeUnit
{

    private final long amount;

    public Milliseconds(long amount) {
        this.amount = amount;
    }

    @Override
    public long toMillis() {
        return amount;
    }

    @Override
    public long toSeconds() {
        return (amount + 500) / 1000;
    }

    @Override
    public long toMinutes() {
        return (amount + 30000) / 60000;
    }

    public long getHours() {
    return (amount + 1800000) / 3600000;
}
}
