package com.inge.nathan.monopolycalculator.MonopolyObjects;

import com.inge.nathan.monopolycalculator.Utilities.MonopolyConstants;
import com.inge.nathan.monopolycalculator.Utilities.NoCurrentGameException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Singleton class containing current Monopoly Game
 */
public class MonopolyGame {

    // Static instance variables
    private static MonopolyGame currentGame;
    private static String savedFileName = "MCSavedGames.ser";

    // Instance variables
    private ArrayList<MonopolyPlayer> players;
    private ArrayList<MonopolyProperty> availableProperties;
    private Date dateModified;
    private String name;

    /**
     * Private constructor to signify static class
     */
    private MonopolyGame() { }

    /**
     * Get the current game
     * @return MonopolyGame the current game
     * @throws NoCurrentGameException thrown if no current game
     */
    public static synchronized MonopolyGame getCurrentGame() throws NoCurrentGameException {
        if(currentGame == null) {
            throw new NoCurrentGameException();
        }

        return currentGame;
    }

    /**
     * Setup a new game
     * @param playerNames list of new player names to create game with
     * @return the new game (set as the current game)
     */
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

    /**
     * Get user's saved games
     * @return user's saved games
     */
    public static ArrayList<MonopolyGame> getSavedGames() {
        ArrayList<MonopolyGame> savedGames = new ArrayList<>();

        return savedGames;
    }

    /**
     * Save user's games
     * @param gamesToSave list of games to save
     */
    public static void saveGames(ArrayList<MonopolyGame> gamesToSave) {

    }

    /**
     * Gets the number of players in the game
     * @return number of players in the game
     */
    public int numPlayers() {
        return players.size();
    }

    /**
     * Sorts the players in the game by total value (cash + property)
     */
    public void sortStandings() {
        Collections.sort(players, (p1, p2) -> Long.compare(p1.getTotalValue(), p2.getTotalValue()));
        Collections.reverse(players);
    }

    /**
     * Removes a property from the given player
     * @param player to remove property from
     * @param property to remove from player
     */
    public void removeProperty(MonopolyPlayer player, MonopolyProperty property) {
        if (this.players.contains(player) && player.getProperties().contains(property)) {
            player.removeProperty(property);
            this.availableProperties.add(new MonopolyProperty(property.getId()));
        }
    }

    /**
     * Add a property to the given player
     * @param player to add property to
     * @param property to add to player
     */
    public void addProperty(MonopolyPlayer player, MonopolyProperty property) {
        if (this.players.contains(player) && !(player.getProperties().contains(property))) {
            player.addProperty(property);
            property.setIsOwned(true);
            this.availableProperties.remove(property);
        }
    }

    /**
     * Updates player properties with the given list of properties
     * @param player to update property list
     * @param properties to set as player's owned properties
     */
    public void updatePlayerProperties(MonopolyPlayer player, ArrayList<MonopolyProperty> properties) {
        for(MonopolyProperty oldProperty : player.getProperties()) {
            this.availableProperties.add(new MonopolyProperty(oldProperty.getId()));
        }
        player.getProperties().clear();

        for(MonopolyProperty newProperty : properties) {
            addProperty(player, newProperty);
        }
    }

    /// MARK - Getters + Setters

    public ArrayList<MonopolyPlayer> getPlayers() {
        return this.players;
    }

    public ArrayList<MonopolyProperty> getAvailableProperties() { return this.availableProperties; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public Date getDateModified() { return this.dateModified; }

    public void setDateModified(Date dateModified) { this.dateModified = dateModified; }

}
