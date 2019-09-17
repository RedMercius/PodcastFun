/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ComedyActivity extends AppCompatActivity {

    Button baBtn;
    Button fbBtn;
    Button mlBtn;
    Button gilBtn;
    Button jbBtn;
    Button bhBtn;
    Button mbBtn;
    Button fkBtn;
    Button ohBtn;
    Button orBtn;
    Button blBtn;

    private final String TAG = "ComedyActivity: ";

    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "jotrpref";
    private boolean fkbPressed = false;
    private boolean ohPressed = false;
    private boolean orPressed = false;
    private boolean blPressed = false;

    private SharedPreferences.Editor editor;

    private RadioTitle rt;
    private boolean listLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs;
        if (listLoaded == false) {
            Log.d(TAG, "Comedy loading list!!");
            rt = RadioTitle.getInstance();

            // Load Comedy Shows
            rt.loadJSONComedy(getApplicationContext());
            rt.updateComedyTitles();
            listLoaded = true;
        }
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedy);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        baBtn = findViewById(R.id.imageBtn1);
        fbBtn = findViewById(R.id.fbBtn);
        mlBtn = findViewById(R.id.mlBtn);
        gilBtn = findViewById(R.id.gilBtn);
        jbBtn =  findViewById(R.id.jbBtn);
        bhBtn = findViewById(R.id.bhBtn);
        mbBtn = findViewById(R.id.mbBtn);
        fkBtn = findViewById(R.id.fkBtn);
        ohBtn = findViewById(R.id.ohBtn);
        orBtn = findViewById(R.id.orBtn);
        blBtn = findViewById(R.id.blBtn);

        fkbPressed = prefs.getBoolean("fkb", false);
        ohPressed = prefs.getBoolean("oh", false);
        orPressed = prefs.getBoolean("or", false);
        blPressed = prefs.getBoolean("bl", false);

        baBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        fbBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        mlBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        gilBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        jbBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        bhBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        mbBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        fkBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        ohBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        orBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        blBtn.setGravity(Gravity.END|Gravity.BOTTOM);

        if (!fkbPressed)
        {
            fkBtn.setTextColor(Color.RED);
            fkBtn.setText(R.string.New_Addition);
        }

        if (!ohPressed)
        {
            ohBtn.setTextColor(Color.RED);
            ohBtn.setText(R.string.New_Addition);
        }

        if (!orPressed)
        {
            orBtn.setTextColor(Color.RED);
            orBtn.setText(R.string.New_Addition);
        }

        if (!blPressed)
        {
            blBtn.setTextColor(Color.RED);
            blBtn.setText(R.string.New_Addition);
        }

        baBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Burns And Allen", getApplicationContext());
                startActivity(i);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Fibber McGee And Molly", getApplicationContext());
                startActivity(i);
            }
        });

        mlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Martin And Lewis", getApplicationContext());
                startActivity(i);
            }
        });

        gilBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("The Great GilderSleeves", getApplicationContext());
                startActivity(i);
            }
        });

        jbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Jack Benny", getApplicationContext());
                startActivity(i);
            }
        });

        bhBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Bob Hope", getApplicationContext());
                startActivity(i);
            }
        });

        mbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Our Miss Brooks", getApplicationContext());
                startActivity(i);
            }
        });

        fkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!fkbPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("fkb", true);
                    editor.apply();
                    Log.d(TAG, "Father Knows Best Pressed!");
                }

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Father Knows Best", getApplicationContext());
                startActivity(i);
            }
        });

        ohBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!ohPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("oh", true);
                    editor.apply();
                    Log.d(TAG, "Ozzie and Harriet Pressed!");
                }

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Ozzie And Harriet", getApplicationContext());
                startActivity(i);
            }
        });

        orBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!orPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("or", true);
                    editor.apply();
                    Log.d(TAG, "Life Of Riley Pressed!");
                }

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("The Life Of Riley", getApplicationContext());
                startActivity(i);
            }
        });

        blBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!blPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("bl", true);
                    editor.apply();
                    Log.d(TAG, "Blondie Pressed!");
                }

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Blondie", getApplicationContext());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (CurrentArtist.getInstance().getCurrentTitle() != null)
        {
            if (CurrentArtist.getInstance().isPlaying())
            {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {

            final Intent i = new Intent(getApplicationContext(), MediaPlay.class);
            i.putExtra("MediaTitle", CurrentArtist.getInstance().getCurrentFile());
            i.putExtra("Selection", CurrentArtist.getInstance().getCurrentArtist());
            i.putExtra("Title", CurrentArtist.getInstance().getCurrentTitle());
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        invalidateOptionsMenu();
    }
}
