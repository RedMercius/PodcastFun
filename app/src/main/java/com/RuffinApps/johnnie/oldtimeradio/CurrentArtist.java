package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;

/**
 * Created by johnnie on 10/27/2017.
 */

public class CurrentArtist {
    private String artist;
    private static CurrentArtist instance = null;

    //a private constructor so no instances can be made outside this class
    private CurrentArtist() {}

    //Everytime you need an instance, call this
    //synchronized to make the call thread-safe
    public static synchronized CurrentArtist getInstance() {
        if(instance == null)
            instance = new CurrentArtist();

        return instance;
    }

    //Initialize this or any other variables in probably the Application class
    public void init(Context context) {
        this.artist = null;
    }

    public void setCurrentArtist(String newArtist)
    {
        this.artist = newArtist;
    }

    public String getCurrentArtist()
    {
        return this.artist;
    }
}