/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

/////////////////////////////////////////////////////////////////////////////
//
/// @class SelectActivity
//
/// @brief SelectActivity class controls navigation for the selected item
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class SelectActivity extends AppCompatActivity {
    // private String TAG = "SelectActivity: ";

    private CustomList adapter;
    private RadioTitle radioList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);

            ListView listview;

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

            String[] titles = getRadioTitles(artist);
            listview = (ListView) findViewById(R.id.listview);

            // set the list adapter
            adapter =
                    new CustomList(this, titles,
                            iconControl.getImageButtonList(), artist);

            listview.setAdapter(adapter);
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
