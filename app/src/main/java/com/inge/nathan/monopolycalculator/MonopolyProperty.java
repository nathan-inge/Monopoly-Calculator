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

    // MARK - Getters + Setters
    public int getId() { return this.id; }

    public String getName() {
        return MonopolyConstants.propertyName(this.id);
    }

    public int getNumHouses() { return this.numHouses; }
    public int getNumHotels() { return this.numHotels; }
    public boolean isMortgaged() { return this.isMortgaged; }

    public int getTotalValue() {
        int value = MonopolyConstants.propertyValue(id);
        int totalValue = (isMortgaged) ? value/2 : value;

        int houseValue = MonopolyConstants.propertyHouseCost(id);

        totalValue += numHouses * houseValue;
        totalValue += (numHotels * houseValue) + (4 * houseValue);

        return totalValue;
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
