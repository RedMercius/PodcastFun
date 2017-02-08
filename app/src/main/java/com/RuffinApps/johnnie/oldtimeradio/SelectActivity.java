/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradio;

/////////////////////////////////////////////////////////////////////////////
//
/// @class SelectActivity
//
/// @brief SelectActivity class controls navigation for the selected item
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import static com.RuffinApps.johnnie.oldtimeradio.R.id.playedList;

public class SelectActivity extends AppCompatActivity {
    private String TAG = "SelectActivity: ";

    private CustomList adapter;
    private CustomList playedAdapter;
    private CustomList notPlayedAdapter;
    private RadioTitle radioList;
    private Button playedListBtn;
    private Button unplayedListBtn;
    private Button allShowsBtn;
    private PlayedList playList;
    private ListView listview;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            Bundle extra = getIntent().getExtras();
            String artist = extra.getString("Selection");

            // get the icon images
            ImageControl iconControl = new ImageControl();

            // get the radio titles
            radioList = new RadioTitle();


            // get and set the instance for the played list
            playList = new PlayedList(this);

            String[] titles = getRadioTitles(artist);

            String playedTitles[] = new String[playList.numberOfRows()];

            Log.d(TAG, "Number of Rows: " + playList.numberOfRows());

            String[] notPlayedTitles = {"NotPlayedTitle"};

            playedTitles = playList.getPlayedTitles();

            listview = (ListView) findViewById(R.id.listview);
            playedListBtn = (Button) findViewById(playedList);
            unplayedListBtn = (Button) findViewById(R.id.unplayedList);
            allShowsBtn = (Button) findViewById(R.id.allShowsList);

            // set the list adapter
            adapter =
                    new CustomList(this, titles,
                            iconControl.getImageButtonList(), artist);

            listview.setAdapter(adapter);

            // TODO: Extract titles from List object
            // set the played adapter
            playedAdapter =
                    new CustomList(this, playedTitles,
                            iconControl.getImageButtonList(), artist);

            // set the notPlayed adapter
            notPlayedAdapter =
                    new CustomList(this, notPlayedTitles,
                            iconControl.getImageButtonList(), artist);

            unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
            unplayedListBtn.setTextColor(Color.BLACK);
            allShowsBtn.setBackgroundColor(Color.BLACK);
            allShowsBtn.setTextColor(Color.WHITE);
            playedListBtn.setBackgroundColor(Color.TRANSPARENT);
            playedListBtn.setTextColor(Color.BLACK);

            playedListBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO: Reset list adapter and modify button to indicate selection.

                    unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    unplayedListBtn.setTextColor(Color.BLACK);
                    allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                    allShowsBtn.setTextColor(Color.BLACK);
                    playedListBtn.setBackgroundColor(Color.BLACK);
                    playedListBtn.setTextColor(Color.WHITE);

                    listview.setAdapter(playedAdapter);
                }
            });

            unplayedListBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO: Reset list adapter and modify button to indicate selection.

                    unplayedListBtn.setBackgroundColor(Color.BLACK);
                    unplayedListBtn.setTextColor(Color.WHITE);
                    allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                    allShowsBtn.setTextColor(Color.BLACK);
                    playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    playedListBtn.setTextColor(Color.BLACK);

                    listview.setAdapter(notPlayedAdapter);
                }
            });

            allShowsBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO: Reset list adapter and modify button to indicate selection.

                    unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    unplayedListBtn.setTextColor(Color.BLACK);
                    allShowsBtn.setBackgroundColor(Color.BLACK);
                    allShowsBtn.setTextColor(Color.WHITE);
                    playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    playedListBtn.setTextColor(Color.BLACK);

                    listview.setAdapter(adapter);
                }
            });
        }

    //TODO: add a boolean for played or unplayed based on button press.
    public String[] getRadioTitles(String artist)
    {
        radioList.initTitles();
        // TODO: control toggle button elements here
        switch (artist)
        {
            case "Burns And Allen":
            {
                int i = 0;
                // TODO: get the values for played from the database here
                String[] titles = new String[radioList.getBurnsAllen().length];
                for (String title : radioList.getBaMap().values()){
                    // TODO: check the database for the title before adding to the list based on
                    // TODO: whether this is a played list or an unplayed list.
                    titles[i] = title;
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

            default: {
                return null;
            }
        }
    }

        @Override
        public void onDestroy()
        {
            // clean up any list activity.
            adapter.cleanUp(this);
            super.onDestroy();
        }
    }
