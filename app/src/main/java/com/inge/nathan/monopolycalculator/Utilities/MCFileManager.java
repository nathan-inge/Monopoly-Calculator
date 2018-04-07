package com.inge.nathan.monopolycalculator.Utilities;


import android.content.Context;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.Utilities.MCExceptions.SavedGamesException;
import com.inge.nathan.monopolycalculator.Utilities.MCExceptions.NoSavedGamesException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class MCFileManager {

    public static final String savedFileName = "MCSavedGames.ser";
    private static final String savedGamesFolderName = "MCSavedGames";

    private MCFileManager() { }

    public static ArrayList<MonopolyGame> getSavedGames(Context context) throws NoSavedGamesException, IOException, ClassNotFoundException {
        ArrayList<MonopolyGame> savedGames;
        FileInputStream fis;
        ObjectInputStream ois;

        File savedFile = new File(context.getFilesDir() + "/" + savedGamesFolderName + "/", savedFileName);

        fis = new FileInputStream(savedFile);
        ois = new ObjectInputStream(fis);
        Object savedGamesObject = ois.readObject();

        savedGames = (ArrayList<MonopolyGame>) savedGamesObject;

        fis.close();
        ois.close();

        return savedGames;
    }

    public static void saveGames(Context context, ArrayList<MonopolyGame> gamesToSave) throws IOException {
        FileOutputStream fos;
        ObjectOutputStream oos;

        File dir = new File(context.getFilesDir(), savedGamesFolderName);
        if(!dir.exists()){
            dir.mkdir();
        }

        File savedFile = new File(context.getFilesDir() + "/" + savedGamesFolderName + "/", savedFileName);

        fos = new FileOutputStream(savedFile);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(gamesToSave);

        fos.close();
        oos.close();
    }

    public static boolean deleteSavedGames(Context context) {
        File savedFile = new File(context.getFilesDir() + "/" + savedGamesFolderName + "/", savedFileName);
        return savedFile.delete();
    }
}


