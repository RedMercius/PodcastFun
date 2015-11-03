/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button comedyButton;
    Button scifiButton;
    Button thrillerButton;
    Button horrorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioTitle radioList = new RadioTitle();

        radioList.run();

        comedyButton = (Button) findViewById(R.id.comedyButton);
        scifiButton = (Button) findViewById(R.id.scifiButton);
        thrillerButton = (Button) findViewById(R.id.thrillerButton);
        horrorButton = (Button) findViewById(R.id.horrorButton);

        comedyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this, ComedyActivity.class);
                startActivity(i);
            }
        });

        scifiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this, SciFiActivity.class);
                startActivity(i);
            }
        });

        thrillerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this, ThrillerActivity.class);
                startActivity(i);
            }
        });

        horrorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this, HorrorActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
