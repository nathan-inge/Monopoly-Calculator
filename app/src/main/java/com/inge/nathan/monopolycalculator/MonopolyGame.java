package com.inge.nathan.monopolycalculator;


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

    private MonopolyGame() { }

    public static synchronized MonopolyGame getCurrentGame() {
        if(currentGame == null) {
            currentGame = new MonopolyGame();
        }

        return currentGame;
    }

    public void setupNewGame(ArrayList<String> playerNames) {
        // Reset current game
        resetGame();

        // Set-up new game
        this.players = new ArrayList<>();

        for(String name : playerNames) {
            players.add(new MonopolyPlayer(name));
        }
    }

    private void resetGame() {
        currentGame = null;
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
