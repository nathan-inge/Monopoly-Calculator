package com.inge.nathan.monopolycalculator;


public class MonopolyPlayer {

    private String name;
    private int total;

    public MonopolyPlayer(String name) {
        this.name = name;
        this.total = 0;
    }


    /// MARK - Getters + Setters
    public String getName() {
        return this.name;
    }

    public int getTotal() { return this.total; }

    public void setName(String name) {
        this.name = name;
    }

}
