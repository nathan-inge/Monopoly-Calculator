package com.inge.nathan.monopolycalculator.MonopolyObjects;

import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;

public class MonopolyProperty {

    private int id;
    private String name;
    private int numHouses;
    private boolean hasHotel;
    private boolean isMortgaged = false;
    private boolean isOwned = false;

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

    public boolean isOwned() { return this.isOwned; }

    public String getName() {
        return MonopolyConstants.propertyName(this.id);
    }

    public int getColor() { return MonopolyConstants.propertyColor(this.id); }

    public int getNumHouses() { return this.numHouses; }

    public boolean hasHotel() { return this.hasHotel; }

    public boolean isMortgaged() { return this.isMortgaged; }

    public int getTotalValue() {
        int value = MonopolyConstants.propertyValue(id);

        if(isMortgaged) {
            return value/2;
        }

        if(hasHotel) {
            return value + 5 * MonopolyConstants.propertyHouseCost(id);
        }

        value += numHouses * MonopolyConstants.propertyHouseCost(id);

        return value;
    }

    public void setNumHouses(int numHouses) {
        if (numHouses > 0) {
            this.hasHotel = false;
            this.isMortgaged = false;
        }

        this.numHouses = numHouses;
    }

    public void setHasHotel(boolean hasHotel) {
        if(hasHotel) {
            this.numHouses = 0;
            this.isMortgaged = false;
        }

        this.hasHotel = hasHotel;
    }

    public void setIsMortgaged(boolean isMortgaged) {
        if(isMortgaged) {
            this.numHouses = 0;
            this.hasHotel = false;
        }
        this.isMortgaged = isMortgaged;
    }

    public void setIsOwned(boolean isOwned) { this.isOwned = isOwned; }

}
