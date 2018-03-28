package com.inge.nathan.monopolycalculator;

import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;

public class MonopolyProperty {

    private int id;
    private String name;
    private int numHouses;
    private int numHotels;
    private boolean isMortgaged = false;

    public MonopolyProperty(int id) {
        this.id = id;
    }

    // MARK - Equality
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!MonopolyProperty.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final MonopolyProperty other = (MonopolyProperty) obj;

        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return (id * 100) / 5;
    }


    // MARK - Getters + Setters
    public int getId() { return this.id; }

    public String getName() {
        return MonopolyConstants.propertyName(this.id);
    }

    public int getColor() { return MonopolyConstants.propertyColor(this.id); }

    public int getNumHouses() { return this.numHouses; }
    public int getNumHotels() { return this.numHotels; }
    public boolean isMortgaged() { return this.isMortgaged; }

    public int getTotalValue() {
        int value = MonopolyConstants.propertyValue(id);

        return value;
    }

    public void setNumHouses(int numHouses) {
        this.numHouses = numHouses;
    }

    public void setNumHotels(int numHotels) {
        this.numHotels = numHotels;
    }

    public void setIsMortgaged(boolean isMortgaged) {
        this.isMortgaged = isMortgaged;
    }

}
