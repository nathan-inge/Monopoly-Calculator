package com.inge.nathan.monopolycalculator;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyPlayer;

import org.junit.Test;

import static org.junit.Assert.*;

public class MonopolyPlayerTest {
    @Test
    public void test_formatMoney() {
        assertEquals("-$4,500", MonopolyPlayer.formatMoney(-4500));
        assertEquals("-$450", MonopolyPlayer.formatMoney(-450));
        assertEquals("-$45", MonopolyPlayer.formatMoney(-45));
        assertEquals("-$4", MonopolyPlayer.formatMoney(-4));
        assertEquals("$0", MonopolyPlayer.formatMoney(0));
        assertEquals("$36", MonopolyPlayer.formatMoney(36));
        assertEquals("$150", MonopolyPlayer.formatMoney(150));
        assertEquals("$2,450", MonopolyPlayer.formatMoney(2450));
        assertEquals("$45,123", MonopolyPlayer.formatMoney(45123));
        assertEquals("$345,123", MonopolyPlayer.formatMoney(345123));
        assertEquals("$6,345,123", MonopolyPlayer.formatMoney(6345123));
        assertEquals("$10,000,000", MonopolyPlayer.formatMoney(10000000));
    }

    @Test
    public void test_formatStanding() {
        assertEquals("1st", MonopolyPlayer.formatStanding(0));
        assertEquals("2nd", MonopolyPlayer.formatStanding(1));
        assertEquals("3rd", MonopolyPlayer.formatStanding(2));
        assertEquals("4th", MonopolyPlayer.formatStanding(3));

        assertEquals("-", MonopolyPlayer.formatStanding(30));
    }

    @Test
    public void test_equals() {
        assertTrue(new MonopolyPlayer("Nate").equals(new MonopolyPlayer("Nate")));
        assertFalse(new MonopolyPlayer("Jeff").equals(new MonopolyPlayer("Mike")));

        MonopolyPlayer p1 = new MonopolyPlayer("Colin");
        p1.setCashValue(50);
        MonopolyPlayer p2 = new MonopolyPlayer("Colin");
        p2.setCashValue(50);

        assertTrue(p1.equals(p2));

        p2.setCashValue(100);
        assertFalse(p1.equals(p2));

        p1.setCashValue(50);
        assertTrue(p1.equals(p2));
    }
}
