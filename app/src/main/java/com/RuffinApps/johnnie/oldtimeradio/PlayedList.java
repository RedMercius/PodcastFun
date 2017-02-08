package com.RuffinApps.johnnie.oldtimeradio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOTAH on 12/19/2016.
 */


public class PlayedList extends SQLiteOpenHelper {

    String TAG = "PlayList";

    private static PlayedList mDbHelper;
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "played.db";
    private static final String PLAYED_TABLE_NAME = "played";
    private static final Integer _ID = 0;
    private static final String SHOW_ID = "show_id";
    private static final String SHOW_TITLE = "radio_show_title";
    private static final String PLAYED_TABLE_CREATE =
            "CREATE TABLE " + PLAYED_TABLE_NAME + "(_id INTEGER,show_id TEXT,radio_show_title TEXT)";

    // TODO: Need to verify the integrity of the database.

    /*public static synchronized PlayedList getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new PlayedList(context.getApplicationContext());
            Log.w("PlayedList_Static", "getInstance");
        }
        return mDbHelper;
    }*/

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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from played", null );
        return res;
    }

    public String[] getPlayedTitles() {
        Cursor rs = getData("Burns And Allen");
        String[] myTitleData = new String[rs.getCount()];
        int i = 0;

        rs.moveToFirst();

        myTitleData[i] = rs.getString(2);
        Log.d(TAG, "Row: " + i + " out of " + rs.getCount() + "\n"  + "Played Titles: " + rs.getString(2));
        i++;

        while(rs.moveToNext())
        {
            myTitleData[i] = rs.getString(2);
            Log.d(TAG, "Row: " + i + " out of " + rs.getCount() + "\n"  + "Played Titles: " + rs.getString(2));
            i++;
        }

        return myTitleData;
    }

    public List<PlayData> getPlayedList()
    {
        List<PlayData> playedList = new ArrayList<>();

        String PLAYED_LIST_SELECT_QUERY = "SELECT * FROM " + PLAYED_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(PLAYED_LIST_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    PlayData playData = new PlayData();
                    playData._id = cursor.getInt(cursor.getColumnIndex("_id"));
                    playData.showId = cursor.getString(cursor.getColumnIndex(SHOW_ID));
                    playData.showTitle = cursor.getString(cursor.getColumnIndex(SHOW_TITLE));

                    playedList.add(playData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return playedList;
    }

    public void add(Integer id, String showId, String title)
    {
        store(id, showId, title);
    }

    public void remove(String showId, String title)
    {
       delete(showId, title);
    }

    /*private PlayedList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }*/

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
        database.delete(showId, title
                + " = " + title, null);

        if (!doesEntryExist(showId, title))
        {
            deleteSuccessful = false;
        }

        return deleteSuccessful;
    }

    private boolean doesEntryExist(String showId, String title)
    {
        boolean entryExists = false;

        List<PlayData> updatedList = getPlayedList();

        for (PlayData playData : updatedList) {
            if (playData.showId.matches(showId) && playData.showTitle.matches(title)) {
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
