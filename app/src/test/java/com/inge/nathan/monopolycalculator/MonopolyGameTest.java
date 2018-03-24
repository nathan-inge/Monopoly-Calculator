package com.inge.nathan.monopolycalculator;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MonopolyGameTest {
    @Test
    public void test_getInstance() {
        MonopolyGame game = MonopolyGame.getInstance();
        assertNotNull(game);
    }

    @Test
    public void test_setupNewGame() {
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jim");
        playerNames.add("Sally");

        MonopolyGame game = MonopolyGame.getInstance();
        game.setupNewGame(playerNames);

        assertEquals(2, game.numPlayers());
        assertEquals("Jim", game.getPlayers().get(0).getName());
        assertEquals("Sally", game.getPlayers().get(1).getName());

        ArrayList<String> newPlayerNames = new ArrayList<>();
        newPlayerNames.add("John");
        newPlayerNames.add("Ali");
        newPlayerNames.add("Geoff");

        game.setupNewGame(newPlayerNames);

        assertEquals(3, game.numPlayers());
        assertEquals("John", game.getPlayers().get(0).getName());
        assertEquals("Ali", game.getPlayers().get(1).getName());
        assertEquals("Geoff", game.getPlayers().get(2).getName());
    }
}