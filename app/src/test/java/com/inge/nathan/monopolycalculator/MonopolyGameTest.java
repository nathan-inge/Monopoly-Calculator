package com.inge.nathan.monopolycalculator;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyProperty;
import com.inge.nathan.monopolycalculator.Utilities.NoCurrentGameException;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants.*;

public class MonopolyGameTest {
    @Test
    public void test_getInstance() {
        try {
            MonopolyGame currentGame = MonopolyGame.getCurrentGame();
            assertTrue(false);
        } catch (NoCurrentGameException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void test_setupNewGame() {
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jim");
        playerNames.add("Sally");

        MonopolyGame game = MonopolyGame.setupNewGame(playerNames);

        assertEquals(2, game.numPlayers());
        assertEquals("Jim", game.getPlayers().get(0).getName());
        assertEquals("Sally", game.getPlayers().get(1).getName());

        ArrayList<String> newPlayerNames = new ArrayList<>();
        newPlayerNames.add("John");
        newPlayerNames.add("Ali");
        newPlayerNames.add("Geoff");

        game = MonopolyGame.setupNewGame(newPlayerNames);

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

        MonopolyGame game = MonopolyGame.setupNewGame(playerNames);

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
        p1.setCashValue(200);
        p2.setCashValue(50);
        p3.setCashValue(300);

        game.sortStandings();
        assertEquals(p3, game.getPlayers().get(0));
        assertEquals(p1, game.getPlayers().get(1));
        assertEquals(p2, game.getPlayers().get(2));
    }

    @Test
    public void test_addProperty() {
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jane");
        playerNames.add("Shannon");
        playerNames.add("Mike");

        MonopolyPlayer invalidPlayer = new MonopolyPlayer("Geoff");

        MonopolyGame game = MonopolyGame.setupNewGame(playerNames);
        assertEquals(28, game.getAvailableProperties().size());

        // Add property to player not in game
        game.addProperty(invalidPlayer, new MonopolyProperty(BOARDWALK));
        assertEquals(28, game.getAvailableProperties().size());
        assertEquals(0, invalidPlayer.getProperties().size());

        // Add property to valid player
        game.addProperty(game.getPlayers().get(0), new MonopolyProperty(PARK_PLACE));
        assertEquals(27, game.getAvailableProperties().size());
        assertEquals(1, game.getPlayers().get(0).getProperties().size());
        assertEquals(new MonopolyProperty(PARK_PLACE), game.getPlayers().get(0).getProperties().get(0));
        assertTrue(game.getPlayers().get(0).getProperties().get(0).isOwned());

        // Add same property to same player
        game.addProperty(game.getPlayers().get(0), new MonopolyProperty(PARK_PLACE));
        assertEquals(27, game.getAvailableProperties().size());
        assertEquals(1, game.getPlayers().get(0).getProperties().size());
    }

    @Test
    public void test_removeProperty() {
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jane");
        playerNames.add("Shannon");
        playerNames.add("Mike");

        MonopolyPlayer invalidPlayer = new MonopolyPlayer("Geoff");

        MonopolyGame game = MonopolyGame.setupNewGame(playerNames);
        assertEquals(28, game.getAvailableProperties().size());

        // Remove property from player not in game
        invalidPlayer.addProperty(new MonopolyProperty(BOARDWALK));
        game.removeProperty(invalidPlayer, new MonopolyProperty(BOARDWALK));
        assertEquals(28, game.getAvailableProperties().size());
        assertEquals(1, invalidPlayer.getProperties().size());

        // Remove property from player with no properties
        game.removeProperty(game.getPlayers().get(1), new MonopolyProperty(KENTUCKY_AVE));
        assertEquals(28, game.getAvailableProperties().size());
        assertEquals(0, game.getPlayers().get(1).getProperties().size());

        // Remove property from player with valid property
        game.addProperty(game.getPlayers().get(2), new MonopolyProperty(BALTIC_AVE));
        assertEquals(27, game.getAvailableProperties().size());
        assertEquals(1, game.getPlayers().get(2).getProperties().size());
        assertTrue(game.getPlayers().get(2).getProperties().get(0).isOwned());

        game.removeProperty(game.getPlayers().get(2), new MonopolyProperty(BALTIC_AVE));
        assertEquals(28, game.getAvailableProperties().size());
        assertEquals(0, game.getPlayers().get(2).getProperties().size());

        // Remove property from player with modified property
        game.addProperty(game.getPlayers().get(0), new MonopolyProperty(CONN_AVE));
        assertEquals(new MonopolyProperty(CONN_AVE), game.getPlayers().get(0).getProperties().get(0));
        game.getPlayers().get(0).getProperties().get(0).setNumHouses(3);

        game.removeProperty(game.getPlayers().get(0), new MonopolyProperty(CONN_AVE));
        assertEquals(28, game.getAvailableProperties().size());
        assertEquals(0, game.getPlayers().get(0).getProperties().size());

        int indexOfConn = game.getAvailableProperties().indexOf(new MonopolyProperty(CONN_AVE));
        assertEquals(0, game.getAvailableProperties().get(indexOfConn).getNumHouses());
        assertFalse(game.getAvailableProperties().get(indexOfConn).isOwned());
    }
}