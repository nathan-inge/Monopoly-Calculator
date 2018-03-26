package com.inge.nathan.monopolycalculator;


import android.widget.ArrayAdapter;

import com.inge.nathan.monopolycalculator.Utilities.NoCurrentGameException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Singleton class containing current Monopoly Game
 */
public class MonopolyGame {

    // Static instance - current game
    private static MonopolyGame currentGame;

    // Instance variables
    private ArrayList<MonopolyPlayer> players;

    private ArrayList<MonopolyProperty> availableProperty;

    private MonopolyGame() { }

    public static synchronized MonopolyGame getCurrentGame() throws NoCurrentGameException {
        if(currentGame == null) {
            throw new NoCurrentGameException();
        }

        return currentGame;
    }

    public static MonopolyGame setupNewGame(ArrayList<String> playerNames) {
        currentGame = new MonopolyGame();

        currentGame.players = new ArrayList<>();
        currentGame.availableProperty = new ArrayList<>();

        for(String name : playerNames) {
            currentGame.players.add(new MonopolyPlayer(name));
        }

        return currentGame;
    }

    public int numPlayers() {
        return players.size();
    }

    public void sortStandings() {
        Collections.sort(players, (p1, p2) -> Long.compare(p1.getTotalValue(), p2.getTotalValue()));
        Collections.reverse(players);
    }

    /// MARK - Getters + Setters

    public ArrayList<MonopolyPlayer> getPlayers() {
        return this.players;
    }
}
