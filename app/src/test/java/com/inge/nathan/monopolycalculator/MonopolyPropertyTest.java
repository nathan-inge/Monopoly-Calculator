package com.inge.nathan.monopolycalculator;


import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyProperty;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants.*;

public class MonopolyPropertyTest {

    @Test
    public void test_getName() {
        assertEquals("Baltic Ave", new MonopolyProperty(BALTIC_AVE).getName());
        assertEquals("St. James Place", new MonopolyProperty(ST_JAMES_PLACE).getName());
        assertEquals("Indiana Ave", new MonopolyProperty(INDIANA_AVE).getName());
        assertEquals("Boardwalk", new MonopolyProperty(BOARDWALK).getName());
        assertEquals("B. & O. Railroad", new MonopolyProperty(BO_RR).getName());
        assertEquals("Water Works", new MonopolyProperty(WATER).getName());
    }

    @Test
    public void test_equals() {
        assertEquals(new MonopolyProperty(PARK_PLACE), new MonopolyProperty(PARK_PLACE));
        assertNotEquals(new MonopolyProperty(ILLINOIS_AVE), new MonopolyProperty(MARVIN_GARDENS));

        MonopolyProperty mp1 = new MonopolyProperty(ST_CHARLES_PLACE);
        mp1.setNumHouses(5);

        MonopolyProperty mp2 = new MonopolyProperty(ST_CHARLES_PLACE);
        mp2.setNumHouses(2);

        assertEquals(mp1, mp2);
    }

    @Test
    public void test_housesAndHotels() {
        MonopolyProperty property = new MonopolyProperty(INDIANA_AVE);

        property.setNumHouses(3);
        assertEquals(3, property.getNumHouses());
        assertFalse(property.hasHotel());

        property.setHasHotel(true);
        assertTrue(property.hasHotel());
        assertEquals(0, property.getNumHouses());

        property.setNumHouses(2);
        assertEquals(2, property.getNumHouses());
        assertFalse(property.hasHotel());

        property.setNumHouses(0);
        assertEquals(0, property.getNumHouses());
        assertFalse(property.hasHotel());

    }

    @Test
    public void test_getTotalValue() {
        assertEquals(60, new MonopolyProperty(BALTIC_AVE).getTotalValue());
        assertEquals(200, new MonopolyProperty(READING_RR).getTotalValue());

        MonopolyProperty property = new MonopolyProperty(KENTUCKY_AVE);
        assertEquals(220, property.getTotalValue());

        property.setIsMortgaged(true);
        assertEquals(110, property.getTotalValue());
        property.setIsMortgaged(false);

        property.setNumHouses(2);
        assertEquals(520, property.getTotalValue());

        property.setHasHotel(true);
        assertEquals(220 + (150 * 5), property.getTotalValue());

        property.setIsMortgaged(true);
        assertEquals(110, property.getTotalValue());
        property.setIsMortgaged(false);

        property.setNumHouses(3);
        assertEquals(220+450, property.getTotalValue());
    }
}
