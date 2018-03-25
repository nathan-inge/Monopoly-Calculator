package com.inge.nathan.monopolycalculator;


import java.text.DecimalFormat;

public class MonopolyPlayer {

    private String name;
    private long cash;


    public MonopolyPlayer(String name) {
        this.name = name;
        this.cash = 0;
    }

    public static String formatMoney(long money) {
        DecimalFormat currencyFormat = new DecimalFormat("'$',###.##");
        return currencyFormat.format(money);
    }

    /// MARK - Getters + Setters
    public String getName() {
        return this.name;
    }
    public long getCash() { return this.cash; }

    public void setName(String name) {
        this.name = name;
    }
    public void setCash(long cash) { this.cash = cash; }

}
