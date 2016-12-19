package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MOTAH on 12/19/2016.
 */

public class PlayedList extends SQLiteOpenHelper {


    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "played.db";
    private static final String PLAYED_TABLE_NAME = "played";
    private static final String KEY_WORD = "show_id";
    private static final String KEY_DEFINITION = "radio_show_title";
    private static final String PLAYED_TABLE_CREATE =
            "CREATE TABLE " + PLAYED_TABLE_NAME + " (" +
                    KEY_WORD + "TEXT, " +
                    KEY_DEFINITION + " TEXT);";

    PlayedList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PLAYED_TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer)
    {
        db.execSQL("DROP TABLE IF EXISTS " + PLAYED_TABLE_NAME);
        onCreate(db);
    }

    public void add(String title)
    {

    }

    public void remove(String title)
    {

    }

    private boolean store(String title)
    {
        return true;
    }

    private boolean delete(String title)
    {
        System.out.println("Comment deleted with title: " + title);
        database.delete(KEY_WORD, KEY_DEFINITION
                + " = " + title, null);
        return true;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


}
