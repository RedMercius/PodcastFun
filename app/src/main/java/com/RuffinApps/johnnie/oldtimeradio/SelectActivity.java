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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
    private String artist;
    private ImageControl iconControl;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            artist = CurrentArtist.getInstance().getCurrentArtist();

            // get the icon images
            iconControl = new ImageControl();

            // get the radio titles
            radioList = new RadioTitle();

            // get and set the instance for the played list
            playList = new PlayedList(this);

            String[] titles = getRadioTitles(artist);
            //String[] playedTitles = new String[playList.numberOfRows()];
            //String[] notPlayedTitles = new String[playList.getUnplayedTitles(artist).length];

            Log.d(TAG, "Unplayed Length: " + playList.getUnplayedTitles(artist).length);

            final String [] notPlayedTitles = playList.getUnplayedTitles(artist);
            final String [] playedTitles = playList.getPlayedTitles(artist);

            listview = findViewById(R.id.listview);
            playedListBtn = findViewById(playedList);
            unplayedListBtn = findViewById(R.id.unplayedList);
            allShowsBtn = findViewById(R.id.allShowsList);

            // set the list adapter
            adapter =
                    new CustomList(this, titles,
                            iconControl.getImageButtonList());

            listview.setAdapter(adapter);

            // set the played adapter
            playedAdapter =
                    new CustomList(this, playedTitles,
                            iconControl.getImageButtonList());


                // set the notPlayed adapter
                notPlayedAdapter =
                        new CustomList(this, notPlayedTitles,
                                iconControl.getImageButtonList());


            unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
            unplayedListBtn.setTextColor(Color.BLACK);
            allShowsBtn.setBackgroundColor(Color.BLACK);
            allShowsBtn.setTextColor(Color.WHITE);
            playedListBtn.setBackgroundColor(Color.TRANSPARENT);
            playedListBtn.setTextColor(Color.BLACK);

            playedListBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    unplayedListBtn.setTextColor(Color.BLACK);
                    allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                    allShowsBtn.setTextColor(Color.BLACK);
                    playedListBtn.setBackgroundColor(Color.BLACK);
                    playedListBtn.setTextColor(Color.WHITE);

                    listview.setAdapter(playedAdapter);
                    if (playedTitles[0].contains("No played shows.")) {
                        playedAdapter.removeButtonsFromView(true);
                    }
                    AdapterState.getInstance().setCurrentState("played");
                }
            });

            unplayedListBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    unplayedListBtn.setBackgroundColor(Color.BLACK);
                    unplayedListBtn.setTextColor(Color.WHITE);
                    allShowsBtn.setBackgroundColor(Color.TRANSPARENT);
                    allShowsBtn.setTextColor(Color.BLACK);
                    playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    playedListBtn.setTextColor(Color.BLACK);

                    listview.setAdapter(notPlayedAdapter);

                    if (notPlayedTitles[0].contains("All shows have been played.")) {
                        notPlayedAdapter.removeButtonsFromView(true);
                    }
                    AdapterState.getInstance().setCurrentState("not_played");
                }
            });

            allShowsBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    unplayedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    unplayedListBtn.setTextColor(Color.BLACK);
                    allShowsBtn.setBackgroundColor(Color.BLACK);
                    allShowsBtn.setTextColor(Color.WHITE);
                    playedListBtn.setBackgroundColor(Color.TRANSPARENT);
                    playedListBtn.setTextColor(Color.BLACK);

                    listview.setAdapter(adapter);
                    AdapterState.getInstance().setCurrentState("all");
                }
            });
        }

        public void update()
        {
            final String [] notPlayedTitles = playList.getUnplayedTitles(artist);
            final String [] playedTitles = playList.getPlayedTitles(artist);

          /*  this.playedAdapter = new CustomList(this, playedTitles,
                            iconControl.getImageButtonList());

            // set the notPlayed adapter
            this.notPlayedAdapter =
                    new CustomList(this, notPlayedTitles,
                            iconControl.getImageButtonList());*/

            this.playedAdapter.radioTitle = playedTitles;
           // this.playedAdapter.notifyDataSetChanged();
           // this.notPlayedAdapter.clear();
            this.notPlayedAdapter.radioTitle = notPlayedTitles;
            this.playedAdapter.notifyDataSetChanged();

            this.notPlayedAdapter.notifyDataSetChanged();

            Log.d(TAG, "In update!!");
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
            notPlayedAdapter.cleanUp(this);
            playedAdapter.cleanUp(this);
            super.onDestroy();
        }
    }