/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

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

import java.util.HashMap;

public class SelectActivity extends AppCompatActivity {
    ListView listview;
    CustomList selectCustomList;


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
            RadioTitle radioList = new RadioTitle();

            String[] titles = getRadioTitles(artist, radioList);

            // set the list adapter
            CustomList adapter =
                    new CustomList(this, titles,
                            iconControl.getImageButtonList(), artist);
            selectCustomList = adapter;

            listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(adapter);
        }

    public String[] getRadioTitles(String artist, RadioTitle radioList)
    {
        String[] titles;

        switch (artist)
        {
            case "Burns And Allen":
            {
                titles = radioList.getBurnsAllen();
                break;
            }

            case "Fibber McGee And Molly":
            {
                titles = radioList.getFibber();
                break;
            }

            case "Martin And Lewis":
            {
                titles = radioList.getMartin();
                break;
            }

            case "The Great GilderSleeves":
            {
                titles = radioList.getGilder();
                break;
            }

            default: {
                titles = null;
                break;
            }
        }

        return titles;
    }
        @Override
        public void onDestroy()
        {
            super.onDestroy();
            System.out.println("OnDestroy");
        }
    }
