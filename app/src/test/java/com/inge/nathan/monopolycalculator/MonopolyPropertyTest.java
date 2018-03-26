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
}
