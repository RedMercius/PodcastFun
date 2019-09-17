package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JSONNav {

    private String TAG = "JSONNav";

    private JSONArray westernObj;
    private JSONArray comedyObj;
    private JSONArray scifiObj;
    private JSONArray thrillerObj;

    private JSONNav() {}

    private static JSONNav instance = null;

    //Everytime you need an instance, call this
    //synchronized to make the call thread-safe
    public static synchronized JSONNav getInstance() {

        if(instance == null)
            instance = new JSONNav();

        return instance;
    }

    public String loadJSONComedy(Context ct) {
        Log.d(TAG, "Loading Comedy!!");
        String json;
        comedyObj = new JSONArray();
        try {
            InputStream is = ct.getAssets().open("JOTRComedyList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            comedyObj = obj.getJSONArray("shows");

            // deallocate objects
             obj = null;
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }
        return json;
    }

    public String loadJSONThriller(Context ct) {
        Log.d(TAG, "Loading Thriller!!");
        String json;
        thrillerObj = new JSONArray();
        try {
            InputStream is = ct.getAssets().open("JOTRThrillerList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            thrillerObj = obj.getJSONArray("shows");

            // deallocate objects
            obj = null;
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }
        return json;
    }

    public String loadJSONSciFi(Context ct) {
        Log.d(TAG, "Loading SciFi!!");
        String json;
        scifiObj = new JSONArray();
        try {
            InputStream is = ct.getAssets().open("JOTRScifiList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            scifiObj = obj.getJSONArray("shows");

            // deallocate objects
            obj = null;
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }
        return json;
    }

    public String loadJSONWestern(Context ct) {
        Log.d(TAG, "Loading Western!!");
        String json;
        westernObj = new JSONArray();
        try {
            InputStream is = ct.getAssets().open("JOTRWesternList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            westernObj = obj.getJSONArray("shows");

            // deallocate objects
            obj = null;
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }
        return json;
    }

    public String[] getListOfEps(String artist, String genre)
    {
        Log.d(TAG, "Getting list of eps!!");
        String[] eps = null;
        int showCount = 0;
        // Retrieve available eps from JSON array.

        try {
            switch (genre) {

                case "Western": {
                    // Log.d(TAG, "Show Count_0: " + westernObj.length());

                    for (int i = 0; i < westernObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (westernObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            showCount++;

                        }
                    }

                    String[] myShows = new String[showCount];
                    int epCount = 0;
                    
                    for (int i = 0; i < westernObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (westernObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            myShows[epCount] = westernObj.getJSONObject(i).get("title").toString();
                            if (myShows[epCount] == null)
                            {
                                Log.d(TAG, "Title is null for artist: " + artist);
                            }
                            epCount++;
                            // Log.d(TAG, "Episode Title: " + showObj.getJSONObject(i).get("title").toString());
                        }
                    }
                    eps = new String[epCount];
                    System.arraycopy(myShows, 0, eps, 0, epCount);
                    break;
                }

                case "Comedy":
                {
                    // Log.d(TAG, "Show Count_0: " + comedyObj.length());
                    for (int i = 0; i < comedyObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (comedyObj.getJSONObject(i).get("artist").toString().equals(artist) &&
                                !comedyObj.getJSONObject(i).get("title").toString().contentEquals("")) {
                            showCount++;
                        }
                    }

                    String[] myShows = new String[showCount];
                    int epCount = 0;

                    for (int i = 0; i < comedyObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (comedyObj.getJSONObject(i).get("artist").toString().equals(artist) &&
                                !comedyObj.getJSONObject(i).get("title").toString().contentEquals("")) {
                            myShows[epCount] = comedyObj.getJSONObject(i).get("title").toString();
                            //Log.d(TAG, "Comedy Show: " + myShows[epCount]);
                            epCount++;
                        }
                    }
                    eps = new String[epCount];
                    System.arraycopy(myShows, 0, eps, 0, epCount);
                    //Log.d(TAG, "Ep Count: " + showCount);
                    //System.arraycopy(myShows, 0, eps, 0, showCount);
                    break;
                }

                case "Thriller":
                {
                    // Log.d(TAG, "Show Count_0: " + thrillerObj.length());
                    for (int i = 0; i < thrillerObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (thrillerObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            showCount++;
                        }
                    }

                    String[] myShows = new String[showCount];
                    int epCount = 0;

                    for (int i = 0; i < thrillerObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (thrillerObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            myShows[epCount] = thrillerObj.getJSONObject(i).get("title").toString();
                            if (myShows[epCount] == null)
                            {
                                Log.d(TAG, "Title is null for artist: " + artist);
                            }
                            epCount++;
                        }
                    }
                    eps = new String[epCount];
                    System.arraycopy(myShows, 0, eps, 0, epCount);
                    break;
                }

                case "Science Fiction":
                {
                    // Log.d(TAG, "Show Count_0: " + showCount);
                    for (int i = 0; i < scifiObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (scifiObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            showCount++;
                        }
                    }

                    String[] myShows = new String[showCount];
                    int epCount = 0;

                    for (int i = 0; i < scifiObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (scifiObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            myShows[epCount] = scifiObj.getJSONObject(i).get("title").toString();

                            if (myShows[epCount] == null)
                            {
                                Log.d(TAG, "Title is null for artist: " + artist);
                            }
                            epCount++;
                        }
                    }
                    eps = new String[epCount];
                    System.arraycopy(myShows, 0, eps, 0, epCount);
                    break;
                }

                default:
                {
                    Log.d(TAG, "ERROR -- Unknown genre!!");
                    break;
                }
            }
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }

        return eps;
    }

    public String getFilename(String title, String artist, String genre)
    {
        String filename = null;
        try {

            switch (genre)
            {
                case "Comedy": {
                    for (int i = 0; i < comedyObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (comedyObj.getJSONObject(i).get("title").toString().equals(title) &&
                                comedyObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            // Log.d(TAG, "Title for File: " + title);
                            filename = comedyObj.getJSONObject(i).get("source").toString();
                        }
                    }
                    break;
                }

                case "Thriller": {
                    for (int i = 0; i < thrillerObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (thrillerObj.getJSONObject(i).get("title").toString().equals(title) &&
                                thrillerObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            filename = thrillerObj.getJSONObject(i).get("source").toString();
                        }
                    }
                    break;
                }

                case "Science Fiction": {
                    for (int i = 0; i < scifiObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (scifiObj.getJSONObject(i).get("title").toString().equals(title) &&
                                scifiObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            filename = scifiObj.getJSONObject(i).get("source").toString();
                        }
                    }
                    break;
                }

                case "Western": {
                    for (int i = 0; i < westernObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (westernObj.getJSONObject(i).get("title").toString().equals(title) &&
                                westernObj.getJSONObject(i).get("artist").toString().equals(artist)) {
                            filename = westernObj.getJSONObject(i).get("source").toString();
                        }
                    }
                    break;
                }
            }
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }

        if (filename == null)
        {
            Log.d(TAG, "Title is null for file: " + title);
        }

        return filename;
    }

    public int getDuration(String title, String artist, String genre)
    {
        int duration = 0;
        // Retrieve duration from JSON file and convert it to integer.

        try {

            switch (genre) {

                case "Comedy": {
                    for (int i = 0; i < comedyObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (comedyObj.getJSONObject(i).get("title").toString().contentEquals(title) &&
                                comedyObj.getJSONObject(i).get("artist").toString().contentEquals(artist)) {

                            duration = Integer.parseInt(comedyObj.getJSONObject(i).get("duration").toString());
                            Log.d(TAG, "Title: " + title);
                            Log.d(TAG, "Artist: " + artist);
                            Log.d(TAG, "Duration: " + duration);
                        }
                    }
                    break;
                }

                case "Thriller": {
                    for (int i = 0; i < thrillerObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (thrillerObj.getJSONObject(i).get("title").toString().contentEquals(title) &&
                                thrillerObj.getJSONObject(i).get("artist").toString().contentEquals(artist)) {

                            duration = Integer.parseInt(thrillerObj.getJSONObject(i).get("duration").toString());
                            Log.d(TAG, "Title: " + title);
                            Log.d(TAG, "Artist: " + artist);
                            Log.d(TAG, "Duration: " + duration);
                        }
                    }
                    break;
                }

                case "Science Fiction": {
                    for (int i = 0; i < scifiObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (scifiObj.getJSONObject(i).get("title").toString().contentEquals(title) &&
                                scifiObj.getJSONObject(i).get("artist").toString().contentEquals(artist)) {

                            duration = Integer.parseInt(scifiObj.getJSONObject(i).get("duration").toString());
                            Log.d(TAG, "Title: " + title);
                            Log.d(TAG, "Artist: " + artist);
                            Log.d(TAG, "Duration: " + duration);
                        }
                    }
                    break;
                }

                case "Western": {
                    for (int i = 0; i < westernObj.length(); i++) {
                        // Here's the secret weapon!!
                        if (westernObj.getJSONObject(i).get("title").toString().contentEquals(title) &&
                                westernObj.getJSONObject(i).get("artist").toString().contentEquals(artist)) {

                            duration = Integer.parseInt(westernObj.getJSONObject(i).get("duration").toString());
                            Log.d(TAG, "Title: " + title);
                            Log.d(TAG, "Artist: " + artist);
                            Log.d(TAG, "Duration: " + duration);
                        }
                    }
                    break;
                }
            }
        }catch(JSONException e)
        {
            Log.d(TAG, "JSON Exception: " + e);
        }
        return duration;
    }
}
