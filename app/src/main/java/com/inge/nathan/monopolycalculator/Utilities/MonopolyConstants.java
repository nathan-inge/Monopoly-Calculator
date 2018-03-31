package com.inge.nathan.monopolycalculator.Utilities;

import android.view.WindowManager;

import com.inge.nathan.monopolycalculator.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MonopolyConstants {

    private MonopolyConstants() { }

    public static final boolean PRO_VERSION = true;

    // Requests
    public static final int REQUEST_EDIT_PLAYER = 1000;

    // Responses
    public static final int PLAYER_EDITTED = 100;

    // Property IDs
    public static final int MED_AVE = 10;
    public static final int BALTIC_AVE = 11;

    public static final int ORIENTAL_AVE = 20;
    public static final int VERMONT_AVE = 21;
    public static final int CONN_AVE = 22;

    public static final int ST_CHARLES_PLACE = 30;
    public static final int STATES_AVE = 31;
    public static final int VIRGINIA_AVE = 32;

    public static final int ST_JAMES_PLACE = 40;
    public static final int TENN_AVE = 41;
    public static final int NY_AVE = 42;

    public static final int KENTUCKY_AVE = 50;
    public static final int INDIANA_AVE = 51;
    public static final int ILLINOIS_AVE = 52;

    public static final int ATLANTIC_AVE = 60;
    public static final int VENTNOR_AVE = 61;
    public static final int MARVIN_GARDENS = 62;

    public static final int PACIFIC_AVE = 70;
    public static final int NC_AVE = 71;
    public static final int PENN_AVE = 72;

    public static final int PARK_PLACE = 80;
    public static final int BOARDWALK = 81;

    public static final int READING_RR = 90;
    public static final int PENN_RR = 91;
    public static final int BO_RR = 92;
    public static final int SHORT_RR = 93;

    public static final int ELECTIC = 95;
    public static final int WATER = 96;

    public static ArrayList<Integer> allProperties() {
        ArrayList<Integer> allProperties = new ArrayList<>();

        allProperties.add(MED_AVE);
        allProperties.add(BALTIC_AVE);

        allProperties.add(ORIENTAL_AVE);
        allProperties.add(VERMONT_AVE);
        allProperties.add(CONN_AVE);

        allProperties.add(ST_CHARLES_PLACE);
        allProperties.add(STATES_AVE);
        allProperties.add(VIRGINIA_AVE);

        allProperties.add(ST_JAMES_PLACE);
        allProperties.add(TENN_AVE);
        allProperties.add(NY_AVE);

        allProperties.add(KENTUCKY_AVE);
        allProperties.add(INDIANA_AVE);
        allProperties.add(ILLINOIS_AVE);

        allProperties.add(ATLANTIC_AVE);
        allProperties.add(VENTNOR_AVE);
        allProperties.add(MARVIN_GARDENS);

        allProperties.add(PACIFIC_AVE);
        allProperties.add(NC_AVE);
        allProperties.add(PENN_AVE);

        allProperties.add(PARK_PLACE);
        allProperties.add(BOARDWALK);

        allProperties.add(READING_RR);
        allProperties.add(PENN_RR);
        allProperties.add(BO_RR);
        allProperties.add(SHORT_RR);

        allProperties.add(WATER);
        allProperties.add(ELECTIC);

        return allProperties;
    }

    // Property Names
    public static String propertyName(int id) {
        switch (id) {
            case MED_AVE:
                return "Mediterranean Ave";
            case BALTIC_AVE:
                return "Baltic Ave";

            case ORIENTAL_AVE:
                return "Oriental Ave";
            case VERMONT_AVE:
                return "Vermont Ave";
            case CONN_AVE:
                return "Connecticut Ave";

            case ST_CHARLES_PLACE:
                return "St. Charles Place";
            case STATES_AVE:
                return "States Ave";
            case VIRGINIA_AVE:
                return "Virginia Ave";

            case ST_JAMES_PLACE:
                return "St. James Place";
            case TENN_AVE:
                return "Tennessee Ave";
            case NY_AVE:
                return "New York Ave";

            case KENTUCKY_AVE:
                return "Kentucky Ave";
            case INDIANA_AVE:
                return "Indiana Ave";
            case ILLINOIS_AVE:
                return "Illinois Ave";

            case ATLANTIC_AVE:
                return "Atlantic Ave";
            case VENTNOR_AVE:
                return "Ventnor Ave";
            case MARVIN_GARDENS:
                return "Marvin Gardens";

            case PACIFIC_AVE:
                return "Pacific Ave";
            case NC_AVE:
                return "North Carolina Ave";
            case PENN_AVE:
                return "Pennsylvania Ave";

            case PARK_PLACE:
                return "Park Place";
            case BOARDWALK:
                return "Boardwalk";

            case READING_RR:
                return "Reading Railroad";
            case PENN_RR:
                return "Pennsylvania Railroad";
            case BO_RR:
                return "B. & O. Railroad";
            case SHORT_RR:
                return "Short Line";

            case ELECTIC:
                return "Electric Company";
            case WATER:
                return "Water Works";

            default:
                return "ID NOT RECOGNIZED";
        }
    }

    // Property Values
    public static int propertyValue(int id) {
        if ( id == MED_AVE || id == BALTIC_AVE) {
            return 60;
        }

        if (id == ORIENTAL_AVE || id == VERMONT_AVE) {
            return 100;
        }

        if (id == CONN_AVE) {
            return 120;
        }

        if (id == ST_CHARLES_PLACE || id == STATES_AVE) {
            return 140;
        }

        if (id == VIRGINIA_AVE) {
            return 160;
        }

        if (id == ST_JAMES_PLACE || id == TENN_AVE) {
            return 180;
        }

        if (id == NY_AVE) {
            return 200;
        }

        if (id == KENTUCKY_AVE || id == INDIANA_AVE) {
            return 220;
        }

        if (id == ILLINOIS_AVE) {
            return 240;
        }

        if (id == ATLANTIC_AVE || id == VENTNOR_AVE) {
            return 260;
        }

        if (id == MARVIN_GARDENS) {
            return 280;
        }

        if (id == PACIFIC_AVE || id ==NC_AVE) {
            return 300;
        }

        if (id == PENN_AVE) {
            return 320;
        }

        if (id == PARK_PLACE) {
            return 350;
        }

        if (id == BOARDWALK) {
            return 400;
        }

        if (id == READING_RR || id == PENN_RR || id == BO_RR || id == SHORT_RR) {
            return 200;
        }

        if (id == ELECTIC || id == WATER) {
            return 150;
        }

        return 0;
    }

    // Property colors
    public static int propertyColor(int id) {
        if (id >= MED_AVE && id <= BALTIC_AVE) {
            return R.color.colorBrown;
        }

        if (id >= ORIENTAL_AVE && id <= CONN_AVE) {
            return R.color.colorLightBlue;
        }

        if (id >= ST_CHARLES_PLACE && id <= VIRGINIA_AVE) {
            return R.color.colorPurple;
        }

        if (id >= ST_JAMES_PLACE && id <= NY_AVE) {
            return R.color.colorOrange;
        }

        if (id >= KENTUCKY_AVE && id <= ILLINOIS_AVE) {
            return R.color.colorRed;
        }

        if (id >= ATLANTIC_AVE && id <= MARVIN_GARDENS) {
            return R.color.colorYellow;
        }

        if (id >= PACIFIC_AVE && id <= PENN_AVE) {
            return R.color.colorGreen;
        }

        if (id >= PARK_PLACE && id <= BOARDWALK) {
            return R.color.colorBlue;
        }

        return R.color.colorGray;
    }

    // Property house costs
    public static int propertyHouseCost(int id) {
        if (id >= MED_AVE && id <= CONN_AVE) {
            return 50;
        }

        if (id >= ST_CHARLES_PLACE && id <= NY_AVE) {
            return 100;
        }

        if (id >= KENTUCKY_AVE && id <= MARVIN_GARDENS) {
            return 150;
        }

        if (id >= PACIFIC_AVE && id <= BOARDWALK) {
            return 200;
        }

        return 0;
    }

}
