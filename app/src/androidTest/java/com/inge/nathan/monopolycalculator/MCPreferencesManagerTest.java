package com.inge.nathan.monopolycalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.inge.nathan.monopolycalculator.Utilities.MCPreferencesManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MCPreferencesManagerTest {
    @Test
    public void test_buyPro() {
        Context context = InstrumentationRegistry.getTargetContext();

        MCPreferencesManager.upgradeToPro(context);

        assertTrue(MCPreferencesManager.getProStatus(context));
    }
}
