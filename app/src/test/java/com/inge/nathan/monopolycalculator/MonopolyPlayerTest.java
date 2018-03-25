package com.inge.nathan.monopolycalculator;

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
}
