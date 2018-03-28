package com.inge.nathan.monopolycalculator.MonopolyObjects;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonopolyPlayer {

    private String name;
    private long cashValue;
    private ArrayList<MonopolyProperty> properties;

    public MonopolyPlayer(String name) {
        this.name = name;
        this.properties = new ArrayList<>();
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

        if (this.cashValue != other.cashValue) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result += cashValue;
        result = result / 5;
        return result;
    }

    /// MARK - Getters + Setters
    public String getName() {
        return this.name;
    }

    public long getTotalValue() {
        return this.cashValue + this.getPropertyValue();
    }

    public long getCashValue() { return this.cashValue; }

    public long getPropertyValue() {
        int value = 0;

        for(MonopolyProperty property : this.properties) {
            value += property.getTotalValue();
        }

        return value;
    }

    public ArrayList<MonopolyProperty> getProperties() { return this.properties; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCashValue(long cashValue) {
        this.cashValue = cashValue;
    }

    public void addProperty(MonopolyProperty property) {
        this.properties.add(property);
    }

    public void removeProperty(MonopolyProperty property) {
        this.properties.remove(property);
    }
}
