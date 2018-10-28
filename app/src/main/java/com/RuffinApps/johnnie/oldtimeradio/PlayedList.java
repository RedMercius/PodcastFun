package com.RuffinApps.johnnie.oldtimeradio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MOTAH on 12/19/2016.
 */


public class PlayedList extends SQLiteOpenHelper {

    private String TAG = "PlayList";

    private static PlayedList mDbHelper;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    private RadioTitle radioList;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "played.db";
    private static final String PLAYED_TABLE_NAME = "played";
    private static final Integer _ID = 0;
    private static final String SHOW_ID = "show_id";
    private static final String SHOW_TITLE = "radio_show_title";
    private static final String PLAYED_TABLE_CREATE =
            "CREATE TABLE " + PLAYED_TABLE_NAME + "(_id INTEGER,show_id TEXT,radio_show_title TEXT)";

    // TODO: Need to verify the integrity of the database.

    public PlayedList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // get the radio titles
        radioList = new RadioTitle();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PLAYED_TABLE_CREATE);
        Log.d(TAG, "OnCreate db");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer)
    {
        if (oldVer != newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + PLAYED_TABLE_NAME);
            onCreate(db);
        }
    }

    public int numberOfRows(){
        database = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(database, PLAYED_TABLE_NAME );
        return numRows;
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String [] artist = {id};
        String query = ( "select * from played where show_id=?" );
        Cursor res =  db.rawQuery( query, artist );
        return res;
    }

    public String[] getPlayedTitles(String artist) {
        Cursor rs = getData(artist);
        String[] myTitleData = new String[rs.getCount()];
        String[] emptyList = {"No played shows."};

        if (rs.getCount() > 0) {
            int i = 0;

            rs.moveToFirst();

            myTitleData[i] = rs.getString(2);
            Log.d(TAG, "Row: " + i + " out of " + rs.getCount() + "\n" + "Played Titles: " + rs.getString(2));
            i++;

            while (rs.moveToNext()) {
                myTitleData[i] = rs.getString(2);
                Log.d(TAG, "Row: " + i + " out of " + rs.getCount() + "\n" + "Played Titles: " + rs.getString(2));
                i++;
            }
            return myTitleData;
        }
        return emptyList;
    }

    public String[] getUnplayedTitles(String artist) {
        Cursor rs = getData(artist);
        String[] myTitleData = new String[rs.getCount()];
        String[] emptyList = getRadioTitles(artist);
        String[] fullList = {"All shows have been played."};

        if (rs.getCount() > 0) {
            int i = 0;

            rs.moveToFirst();

            myTitleData[i] = rs.getString(2);
            i++;

            while (rs.moveToNext()) {
                myTitleData[i] = rs.getString(2);
                i++;
            }

            String[] allTitles = getRadioTitles(artist);
            String[] unplayedBuilder = new String[(allTitles.length - myTitleData.length)];
            int unplayedCount = 0;

            for (int b = 0; b < (allTitles.length); ++b) {
                if (b < myTitleData.length) {
                    if (myTitleData[b].equals(allTitles[b])) {
                        // if what exists in the databse equals one of the titles, do not add it.
                        Log.d(TAG, "Played_Titles: " + allTitles[b]);
                    }
                } else {
                    // otherwise, the rest are unplayed
                    unplayedBuilder[unplayedCount] = allTitles[b];
                    unplayedCount++;
                    Log.d(TAG, "Unplayed_Titles: " + allTitles[b]);
                }
            }

            // if the count matches all titles, we have played everything that exists
            if (unplayedCount == allTitles.length)
            {
                return fullList;
            }
            return unplayedBuilder;
        }
        return emptyList;
    }

    public String[] getRadioTitles(String artist)
    {
        radioList.initTitles();
        switch (artist)
        {
            case "Burns And Allen":
            {
                int i = 0;
                String[] titles = new String[radioList.getBurnsAllen().length];
                for (String title : radioList.getBaMap().values()){
                    titles[i] = title;
                    Log.d(TAG, "BurnsAndAllen_Titles: " + title);
                    i++;
                }
                return titles;
            }

            case "Fibber McGee And Molly":
            {
                int i = 0;
                String[] titles = new String[radioList.getFibber().length];
                for (String title : radioList.getFbMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Martin And Lewis":
            {
                int i = 0;

                String[] titles = new String[radioList.getMartin().length];
                for (String title : radioList.getMlMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "The Great GilderSleeves":
            {
                int i = 0;
                String[] titles = new String[radioList.getGilder().length];
                for (String title : radioList.getGlMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Jack Benny":
            {
                int i = 0;
                String[] titles = new String[radioList.getJackBenny().length];
                for (String title : radioList.getJbMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Bob Hope":
            {
                int i = 0;
                String[] titles = new String[radioList.getBobHope().length];
                for (String title : radioList.getBhMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "XMinus1":
            {
                int i = 0;
                String[] titles = new String[radioList.getXM().length];
                for (String title : radioList.getXMMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Inner Sanctum":
            {
                int i = 0;
                String[] titles = new String[radioList.getIs().length];
                for (String title : radioList.getIsMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Dimension X":
            {
                int i = 0;
                String[] titles = new String[radioList.getDx().length];
                for (String title : radioList.getDxMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Night Beat":
            {
                int i = 0;
                String[] titles = new String[radioList.getnb().length];
                for (String title : radioList.getNbMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Speed":
            {
                int i = 0;
                String[] titles = new String[radioList.getsg().length];
                for (String title : radioList.getSgMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "The Whistler":
            {
                int i = 0;
                String[] titles = new String[radioList.getws().length];
                for (String title : radioList.getWsMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Hopalong Cassidy":
            {
                int i = 0;
                String[] titles = new String[radioList.gethc().length];
                for (String title : radioList.getHcMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            case "Fort Laramie":
            {
                int i = 0;
                String[] titles = new String[radioList.getfl().length];
                for (String title : radioList.getFlMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }
            case "Miss Brooks":
            {
                int i = 0;
                String[] titles = new String[radioList.getMissBrooks().length];
                for (String title : radioList.getMbMap().values())
                {
                    titles[i] = title;
                    i++;
                }
                return titles;
            }

            default: {
                Log.d(TAG, "Returning Null!!");
                return null;
            }
        }
    }

    public void add(Integer id, String showId, String title)
    {
        // if entry exists, do not store.
        if ( doesEntryExist(showId, title) )
        {
            return;
        }
        store(id, showId, title);
    }

    public void remove(String showId, String title)
    {
        // if the entry exists, delete it.
       if(doesEntryExist(showId, title)) {
           delete(showId, title);
       }
    }

    private boolean store(Integer id, String showId, String title)
    {
        boolean successfulStore = true;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("_id", id);
            values.put(SHOW_ID, showId);
            values.put(SHOW_TITLE, title);
            db.insert(PLAYED_TABLE_NAME, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while attempting add to Played database.");
            successfulStore = false;
        }
        finally {
            db.close();
        }
        return successfulStore;
    }

    private boolean delete(String showId, String title)
    {
        boolean deleteSuccessful = true;

        Log.d(TAG, "Comment deleted with title: " + title);
        SQLiteDatabase db = this.getWritableDatabase();

        String [] show = {showId, title};

        try {
            db.delete("played", "show_id=? and radio_show_title=?", show);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while attempting add to Played database.");
            deleteSuccessful = false;
        }
        finally {
            db.close();
        }
        return deleteSuccessful;
    }

    private boolean doesEntryExist(String showId, String title)
    {
        boolean entryExists = false;

        String [] updatedList = getPlayedTitles(showId);

        for (int i = 0; i < updatedList.length; i++) {
            if (updatedList[i].contentEquals(title)) {
                entryExists = true;
            }
        }

        return entryExists;
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    public void close() {
        database.close();
    }
}