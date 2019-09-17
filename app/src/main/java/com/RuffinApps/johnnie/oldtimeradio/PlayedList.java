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

    //private static PlayedList mDbHelper;
    private SQLiteDatabase database;
    // private SQLiteOpenHelper dbHelper;

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
        database = this.getReadableDatabase();
        String [] artist = {id};
        String query = ( "select * from played where show_id=?" );
        Cursor res =  database.rawQuery( query, artist );
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
            rs.close();
            return myTitleData;
        }
        rs.close();
        return emptyList;
    }

    public String[] getUnplayedTitles(String artist) {
        Cursor rs = getData(artist);
        String[] myTitleData = new String[rs.getCount()];
        String[] emptyList = CurrentArtist.getInstance().getRadioTitles(artist);
        //Log.d(TAG, "Got radio titles!! Unplayed List Length: " + emptyList.length);

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

            String[] allTitles = CurrentArtist.getInstance().getRadioTitles(artist);
            // Log.d(TAG, "Got radio titles!! Title Length: " + allTitles.length);
            String[] unplayedBuilder = new String[(allTitles.length - myTitleData.length)];
            int unplayedCount = 0;

            for (int b = 0; b < (allTitles.length); ++b) {
                if (b < myTitleData.length) {
                    if (myTitleData[b].equals(allTitles[b])) {
                        // if what exists in the databse equals one of the titles, do not add it.
                        // Log.d(TAG, "Played_Titles: " + allTitles[b]);
                    }
                } else {
                    // otherwise, the rest are unplayed
                    unplayedBuilder[unplayedCount] = allTitles[b];
                    unplayedCount++;
                    // Log.d(TAG, "Unplayed_Titles: " + allTitles[b]);
                }
            }

            // if the count matches all titles, we have played everything that exists
            if (unplayedCount == allTitles.length)
            {
                rs.close();
                return fullList;
            }
            rs.close();
            return unplayedBuilder;
        }
        rs.close();
        return emptyList;
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
