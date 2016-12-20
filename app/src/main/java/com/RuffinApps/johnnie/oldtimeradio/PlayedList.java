package com.RuffinApps.johnnie.oldtimeradio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Struct;
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
    private static final String SHOW_ID = "show_id";
    private static final String SHOW_TITLE = "radio_show_title";
    private static final String PLAYED_TABLE_CREATE =
            "CREATE TABLE " + PLAYED_TABLE_NAME + " (" +
                    SHOW_ID + "TEXT, " +
                    SHOW_TITLE + " TEXT);";

    public static synchronized PlayedList getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.

        if (mDbHelper == null) {
            mDbHelper = new PlayedList(context.getApplicationContext());
        }
        return mDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PLAYED_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer)
    {
        if (oldVer != newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + PLAYED_TABLE_NAME);
            onCreate(db);
        }
    }

   /* public List<String> getPlayedList()
    {
        List<String> playedList = new ArrayList<>();

        String PLAYED_LIST_SELECT_QUERY = "SELECT * FROM " + PLAYED_TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(PLAYED_LIST_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {

                    playData.showId = cursor.getString(cursor.getColumnIndex(NAME));
                    userData.college = cursor.getString(cursor.getColumnIndex(COLLEGE));
                    userData.place = cursor.getString(cursor.getColumnIndex(PLACE));
                    userData.user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
                    userData.number = cursor.getString(cursor.getColumnIndex(NUMBER));


                    usersdetail.add(userData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }*/

    public void add(String showId, String title)
    {
        store(showId, title);
    }

    public void remove(String showId, String title)
    {
       delete(showId, title);
    }

    private PlayedList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private boolean store(String showId, String title)
    {
        boolean successfulStore = true;

        // Make database writable.
        open();

        database.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(SHOW_ID, showId);
            values.put(SHOW_TITLE, title);
            database.insertOrThrow(PLAYED_TABLE_NAME, null, values);
            database.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while attempting add to Played database.");
            successfulStore = false;
        }
        finally {
            database.endTransaction();
            close();
        }
        return successfulStore;
    }

    private boolean delete(String showId, String title)
    {
        boolean deleteSuccessful = true;

        Log.d(TAG, "Comment deleted with title: " + title);
        database.delete(showId, title
                + " = " + title, null);
        // TODO: Input query to verify that the entry has been deleted.
        return deleteSuccessful;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
