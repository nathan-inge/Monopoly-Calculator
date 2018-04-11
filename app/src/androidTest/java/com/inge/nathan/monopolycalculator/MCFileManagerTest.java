package com.inge.nathan.monopolycalculator;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.Utilities.MCFileManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MCFileManagerTest {

    @Test
    public void test_getSavedGames() {
        Context context = InstrumentationRegistry.getTargetContext();

        // Reset saved games file
        MCFileManager.deleteSavedGames(context);

        ArrayList<MonopolyGame> savedGames = null;
        try {
            savedGames = MonopolyGame.getSavedGames(context);
        } catch (IOException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);

        } catch (ClassNotFoundException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);
        }

        assertEquals(0, savedGames.size());

        // Reset saved games file
        MCFileManager.deleteSavedGames(context);
    }

    @Test
    public void test_saveGames() {
        Context context = InstrumentationRegistry.getTargetContext();

        // Reset saved games file
        MCFileManager.deleteSavedGames(context);

        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Jim");
        playerNames.add("Sally");

        MonopolyGame game = MonopolyGame.setupNewGame(playerNames);

        try {
            MonopolyGame.saveCurrentGame(context, "Game #1");
        } catch (IOException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);

        } catch (ClassNotFoundException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);
        }

        playerNames.add("Yo mama");
        MonopolyGame game2 = MonopolyGame.setupNewGame(playerNames);

        try {
            MonopolyGame.saveCurrentGame(context, "Game #2");
        } catch (IOException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);

        } catch (ClassNotFoundException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);
        }

        try {
            MonopolyGame.saveCurrentGame(context, "Game #3");
        } catch (IOException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);

        } catch (ClassNotFoundException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);
        }

        ArrayList<MonopolyGame> savedGames = null;
        try {
            savedGames = MonopolyGame.getSavedGames(context);
        } catch (IOException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);

        } catch (ClassNotFoundException e) {
            Log.e("MC Integration Tests", "exception", e);
            assertTrue(false);
        }

        assertEquals(3, savedGames.size());

        assertEquals("Game #3", savedGames.get(0).getName());
        assertEquals(3, savedGames.get(0).numPlayers());

        assertEquals("Game #2", savedGames.get(1).getName());
        assertEquals(3, savedGames.get(1).numPlayers());

        assertEquals("Game #1", savedGames.get(2).getName());
        assertEquals(2, savedGames.get(2).numPlayers());

        // Reset saved games file
        MCFileManager.deleteSavedGames(context);
    }
}