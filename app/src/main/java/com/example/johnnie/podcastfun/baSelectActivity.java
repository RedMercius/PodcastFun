/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

/////////////////////////////////////////////////////////////////////////////
//
/// @class baSelectActivity
//
/// @brief baSelectActivity class controls navigation for the selected item
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class baSelectActivity extends AppCompatActivity {
    ListView listview;
    CustomList selectCustomList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);

            // get the icon images
            ImageControl iconControl = new ImageControl();

            // get the radio titles
            RadioTitle radioList = new RadioTitle();

            String artist = "ba";

            // set the list adapter
            CustomList adapter =
                    new CustomList(this, radioList.getBurnsAllen(),
                            iconControl.getImageButtonList(), artist);
            selectCustomList = adapter;

            listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(adapter);
        }

        @Override
        public void onDestroy()
        {
            super.onDestroy();
            System.out.println("OnDestroy");
        }
    }
