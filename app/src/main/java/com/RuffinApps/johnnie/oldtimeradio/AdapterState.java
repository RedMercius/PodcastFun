package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;

/**
 * Created by johnnie on 10/27/2017.
 */

public class AdapterState {
    private String currentState;
    private static AdapterState instance = null;

    //a private constructor so no instances can be made outside this class
    private AdapterState() {}

    //Everytime you need an instance, call this
    //synchronized to make the call thread-safe
    public static synchronized AdapterState getInstance() {
        if(instance == null)
            instance = new AdapterState();

        return instance;
    }

    //Initialize this or any other variables in probably the Application class
    public void init(Context context) {
        this.currentState = "all";
    }

    public void setCurrentState(String newState)
    {
        this.currentState = newState;
    }

    public String getCurrentState()
    {
        return this.currentState;
    }
}