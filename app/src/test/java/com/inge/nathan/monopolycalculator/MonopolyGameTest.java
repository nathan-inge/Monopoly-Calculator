package com.inge.nathan.monopolycalculator;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MonopolyGameTest {
    @Test
    public void test_getInstance() {
        MonopolyGame game = MonopolyGame.getCurrentGame();
        assertNotNull(game);
    }

    @Test
    public void test_setupNewGame() {
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jim");
        playerNames.add("Sally");

        MonopolyGame game = MonopolyGame.getCurrentGame();
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

    @Test
    public void test_sortStandings() {
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jane");
        playerNames.add("Shannon");
        playerNames.add("Mike");

        MonopolyGame game = MonopolyGame.getCurrentGame();
        game.setupNewGame(playerNames);

        // Default standings
        MonopolyPlayer p1 = game.getPlayers().get(0);
        MonopolyPlayer p2 = game.getPlayers().get(1);
        MonopolyPlayer p3 = game.getPlayers().get(2);

        assertEquals(p1, game.getPlayers().get(0));
        assertEquals(p2, game.getPlayers().get(1));
        assertEquals(p3, game.getPlayers().get(2));

        // 1)p2 2)p3 3)p1
        p1.setCashValue(50);
        p2.setCashValue(150);
        p3.setCashValue(75);

        game.sortStandings();
        assertEquals(p2, game.getPlayers().get(0));
        assertEquals(p3, game.getPlayers().get(1));
        assertEquals(p1, game.getPlayers().get(2));

        // 1)p3 2)p1 3)p2
        p1.setPropertyValue(200);
        p2.setPropertyValue(50);
        p3.setPropertyValue(300);

        game.sortStandings();
        assertEquals(p3, game.getPlayers().get(0));
        assertEquals(p1, game.getPlayers().get(1));
        assertEquals(p2, game.getPlayers().get(2));
    }
}