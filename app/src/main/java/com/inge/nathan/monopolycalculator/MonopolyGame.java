package com.inge.nathan.monopolycalculator;


import java.util.ArrayList;

/**
 * Singleton class containing current Monopoly Game
 */
public class MonopolyGame {

    // Static instance - current game
    private static MonopolyGame currentGame;

    // Instance variables
    private ArrayList<MonopolyPlayer> players;

    private MonopolyGame() { }

    public static synchronized MonopolyGame getInstance() {
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
        this.players = null;
    }

    public int numPlayers() {
        return players.size();
    }

    /// MARK - Getters + Setters

    public ArrayList<MonopolyPlayer> getPlayers() {
        return this.players;
    }
}
