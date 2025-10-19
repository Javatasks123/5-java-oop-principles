package com.example.task02;

public class DiscountBill extends Bill
{
    private final int discountPercent;

    public int getDiscountPercent()
    {
        return discountPercent;
    }

    public DiscountBill(int discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    @Override
    public long getPrice()
    {
        long originalPrice = super.getPrice();
        return originalPrice - getDiscountAmount();
    }

    public long getDiscountAmount()
    {
        return (super.getPrice() * discountPercent) / 100;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\nСкидка: ").append(discountPercent).append("%");
        sb.append("\nРазмер скидки: ").append(getDiscountAmount());
        sb.append("\nИтого со скидкой: ").append(getPrice());
        return sb.toString();
    }
}
