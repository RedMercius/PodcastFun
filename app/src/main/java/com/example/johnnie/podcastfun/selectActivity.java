/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

/////////////////////////////////////////////////////////////////////////////
//
/// @class selectActivity
//
/// @brief selectActivity class controls navigation for the selected item
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.IOException;

public class selectActivity extends AppCompatActivity {
    ListView listview;
    MediaControl mc;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select);

            // get the icon images
            ImageControl iconControl = new ImageControl();

            // get the radio titles
            RadioTitle radiolist = new RadioTitle();

            // set the list adapter
            CustomList adapter =
                    new CustomList(this, radiolist.getBurnsAllen(),
                            iconControl.getImageButtonList(), iconControl.getImageButtonListStop());

            // initialize the mediaControl
           /* mc =
                    new MediaControl(this, radiolist.getBurnsAllen(),
                            iconControl.getImageButtonList(), iconControl.getImageButtonListStop());*/

            listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(adapter);
            /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    try {
                        mc.callMediaFromRaw(item, selectActivity.this);
                    } catch (IOException e) {
                        Log.e("tag", e.getMessage(), e);
                    }
                }
            });*/
        }

        @Override
        public void onDestroy()
        {
            super.onDestroy();
            System.out.println("OnDestroy");
        }
    }
