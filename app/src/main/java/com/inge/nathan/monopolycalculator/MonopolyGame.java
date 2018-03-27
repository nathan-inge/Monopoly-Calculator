package com.inge.nathan.monopolycalculator;

import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;
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

    private ArrayList<MonopolyProperty> availableProperties;

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
        currentGame.availableProperties = new ArrayList<>();

        for(String name : playerNames) {
            currentGame.players.add(new MonopolyPlayer(name));
        }

        for(int id : MonopolyConstants.allProperties()) {
            currentGame.availableProperties.add(new MonopolyProperty(id));
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

    public void removeProperty(MonopolyPlayer player, MonopolyProperty property) {
        if (this.players.contains(player) && player.getProperties().contains(property)) {
            player.removeProperty(property);
            this.availableProperties.add(new MonopolyProperty(property.getId()));
        }
    }

    public void addProperty(MonopolyPlayer player, MonopolyProperty property) {
        if (this.players.contains(player) && !(player.getProperties().contains(property))) {
            player.addProperty(property);
            this.availableProperties.remove(property);
        }
    }

    /// MARK - Getters + Setters

    public ArrayList<MonopolyPlayer> getPlayers() {
        return this.players;
    }

    public ArrayList<MonopolyProperty> getAvailableProperties() { return this.availableProperties; }

}
