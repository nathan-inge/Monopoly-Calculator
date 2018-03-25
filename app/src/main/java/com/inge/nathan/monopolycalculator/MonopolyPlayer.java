package com.inge.nathan.monopolycalculator;
import java.text.DecimalFormat;

public class MonopolyPlayer {

    private String name;
    private long totalValue;
    private long cashValue;
    private long propertyValue;

    public MonopolyPlayer(String name) {
        this.name = name;
    }

    public static String formatMoney(long amount) {
        DecimalFormat currencyFormat = new DecimalFormat("'$',###.##");
        return currencyFormat.format(amount);
    }

    public static String formatStanding(int pos) {
        switch (pos) {
            case 0:
                return "1st";
            case 1:
                return "2nd";
            case 2:
                return "3rd";
            case 3:
                return "4th";
            default:
                return "-";
        }
    }

    /// MARK - Overrides
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!MonopolyPlayer.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final MonopolyPlayer other = (MonopolyPlayer) obj;

        if (this.name != other.name) {
            return false;
        }

        if (this.totalValue != other.totalValue) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result += totalValue;
        result = result / 5;
        return result;
    }

    /// MARK - Getters + Setters
    public String getName() {
        return this.name;
    }
    public long getTotalValue() { return this.totalValue; }
    public long getCashValue() { return this.cashValue; }
    public long getPropertyValue() { return this.propertyValue; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCashValue(long cashValue) {
        this.cashValue = cashValue;
        this.totalValue = this.cashValue + this.propertyValue;
    }

    public void setPropertyValue(long propertyValue) {
        this.propertyValue = propertyValue;
        this.totalValue = this.cashValue + this.propertyValue;
    }
}
