package com.inge.nathan.monopolycalculator;


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
        mp1.setNumHotels(5);

        MonopolyProperty mp2 = new MonopolyProperty(ST_CHARLES_PLACE);
        mp2.setNumHotels(2);

        assertEquals(mp1, mp2);
    }
}
