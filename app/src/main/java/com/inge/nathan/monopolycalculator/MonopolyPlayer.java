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
