package com.inge.nathan.monopolycalculator.Utilities;


import android.content.Context;

import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyPlayer;
import com.inge.nathan.monopolycalculator.Utilities.MCExceptions.GetSavedGamesException;
import com.inge.nathan.monopolycalculator.Utilities.MCExceptions.NoSavedGamesException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class MCFileManager {

    private static final String savedFileName = "MCSavedGames.ser";

    private MCFileManager() { }

    public static ArrayList<MonopolyGame> getSavedGames(Context context) throws GetSavedGamesException, NoSavedGamesException {
        ArrayList<MonopolyGame> savedGames;
        FileInputStream fis;
        ObjectInputStream ois;

        try {
            fis = context.openFileInput(savedFileName);
            ois = new ObjectInputStream(fis);
            Object savedGamesObject = ois.readObject();

            if (!(savedGamesObject instanceof ArrayList<?>)) {
                throw new GetSavedGamesException(new ClassCastException());

            } else {
                savedGames = (ArrayList<MonopolyGame>) savedGamesObject;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new NoSavedGamesException();

        } catch (IOException e) {
            e.printStackTrace();
            throw new GetSavedGamesException(new IOException());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new GetSavedGamesException(new ClassNotFoundException());
        }

        return savedGames;
    }

    public static void saveGames(ArrayList<MonopolyGame> gamesToSave) {

    }
}
