package com.RuffinApps.johnnie.oldtimeradio;
/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

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

// TODO: Don't forget about hardware buttons

public class ThrillerActivity extends AppCompatActivity {

    Button nbBtn;
    Button sgBtn;
    Button wsBtn;
    Button abmBtn;
    Button adcBtn;
    Button bbBtn;
    Button bvBtn;
    Button cbsBtn;
    Button daBtn;
    Button dtBtn;
    Button mmnBtn;
    Button qpBtn;
    Button hlBtn;
    Button ssBtn;

    // MY_PREFS_NAME - a static String variable like:
    public static final String MY_PREFS_NAME = "jotrpref";
    private SharedPreferences.Editor editor;

    private boolean abmPressed = false;
    private boolean adcPressed = false;
    private boolean bbPressed = false;
    private boolean bvPressed = false;
    private boolean cbsPressed = false;
    private boolean daPressed = false;
    private boolean dtPressed = false;
    private boolean mmnPressed = false;
    private boolean qpPressed = false;
    private boolean hlPressed = false;
    private boolean ssPressed = false;

    private String TAG = "Thriller Activity: ";

    private boolean listLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thriller);
        if (!listLoaded) {
            Log.d(TAG, "Thriller list not loaded. Loading...");
            RadioTitle rt = RadioTitle.getInstance();

            // Load Thriller Shows
            rt.loadJSONThriller(getApplicationContext());
            rt.updateThrillerTitles();
            listLoaded = true;
        }

        SharedPreferences prefs;
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nbBtn = findViewById(R.id.nbBtn);
        sgBtn = findViewById(R.id.sgBtn);
        wsBtn = findViewById(R.id.wsBtn);
        abmBtn= findViewById(R.id.abmBtn);
        adcBtn= findViewById(R.id.adcBtn);
        bbBtn= findViewById(R.id.bbBtn);
        bvBtn= findViewById(R.id.bvBtn);
        cbsBtn= findViewById(R.id.cbsBtn);
        daBtn= findViewById(R.id.daBtn);
        dtBtn= findViewById(R.id.dtBtn);
        mmnBtn= findViewById(R.id.mmnBtn);
        qpBtn= findViewById(R.id.qpBtn);
        hlBtn= findViewById(R.id.hlBtn);
        ssBtn= findViewById(R.id.ssBtn);

        abmPressed = prefs.getBoolean("abm", false);
        adcPressed = prefs.getBoolean("adc", false);
        bbPressed = prefs.getBoolean("bb", false);
        bvPressed = prefs.getBoolean("bv", false);
        cbsPressed = prefs.getBoolean("cbs", false);
        daPressed = prefs.getBoolean("da", false);
        dtPressed = prefs.getBoolean("dt", false);
        mmnPressed = prefs.getBoolean("mmn", false);
        qpPressed = prefs.getBoolean("qp", false);
        hlPressed = prefs.getBoolean("hl", false);
        ssPressed = prefs.getBoolean("ss", false);

        nbBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        sgBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        wsBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        abmBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        adcBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        bbBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        bvBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        cbsBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        daBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        dtBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        mmnBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        qpBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        hlBtn.setGravity(Gravity.END|Gravity.BOTTOM);
        ssBtn.setGravity(Gravity.END| Gravity.BOTTOM);

        if (!abmPressed)
        {
            abmBtn.setTextColor(Color.RED);
            abmBtn.setText(R.string.New_Addition);
        }

        if (!adcPressed)
        {
            adcBtn.setTextColor(Color.RED);
            adcBtn.setText(R.string.New_Addition);
        }

        if (!bbPressed)
        {
            bbBtn.setTextColor(Color.RED);
            bbBtn.setText(R.string.New_Addition);
        }

        if (!bvPressed)
        {
            bvBtn.setTextColor(Color.RED);
            bvBtn.setText(R.string.New_Addition);
        }
        if (!cbsPressed)
        {
            cbsBtn.setTextColor(Color.RED);
            cbsBtn.setText(R.string.New_Addition);
        }
        if (!daPressed)
        {
            daBtn.setTextColor(Color.RED);
            daBtn.setText(R.string.New_Addition);
        }
        if (!dtPressed)
        {
            dtBtn.setTextColor(Color.RED);
            dtBtn.setText(R.string.New_Addition);
        }
        if (!mmnPressed)
        {
            mmnBtn.setTextColor(Color.RED);
            mmnBtn.setText(R.string.New_Addition);
        }
        if (!qpPressed)
        {
            qpBtn.setTextColor(Color.RED);
            qpBtn.setText(R.string.New_Addition);
        }
        if (!hlPressed)
        {
            hlBtn.setTextColor(Color.RED);
            hlBtn.setText(R.string.New_Addition);
        }
        if (!ssPressed)
        {
            ssBtn.setTextColor(Color.RED);
            ssBtn.setText(R.string.New_Addition);
        }

        nbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Night Beat", getApplicationContext());
                startActivity(i);
            }
        });

        sgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Speed Gibson", getApplicationContext());
                startActivity(i);
            }
        });

        wsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("The Whistler", getApplicationContext());
                startActivity(i);
            }
        });

        abmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!abmPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("abm", true);
                    editor.apply();
                    Log.d(TAG, "Adventures By Morse Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Adventures By Morse", getApplicationContext());
                startActivity(i);
            }
        });
        adcBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!adcPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("adc", true);
                    editor.apply();
                    Log.d(TAG, "Adventures of Dick Cole Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Adventures of Dick Cole", getApplicationContext());
                startActivity(i);
            }
        });

        bbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!bbPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("bb", true);
                    editor.apply();
                    Log.d(TAG, "Boston Blackie Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Boston Blackie", getApplicationContext());
                startActivity(i);
            }
        });

        bvBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!bvPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("bv", true);
                    editor.apply();
                    Log.d(TAG, "Bold Venture Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Bold Venture", getApplicationContext());
                startActivity(i);
            }
        });
        cbsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!cbsPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("cbs", true);
                    editor.apply();
                    Log.d(TAG, "CBS Mystery Theater Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("CBS Radio Mystery Theater", getApplicationContext());
                startActivity(i);
            }
        });
        daBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!daPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("da", true);
                    editor.apply();
                    Log.d(TAG, "Dangerous Assignment Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Dangerous Assignment", getApplicationContext());
                startActivity(i);
            }
        });
        dtBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!dtPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("dt", true);
                    editor.apply();
                    Log.d(TAG, "Duffys Tavern Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Duffys Tavern", getApplicationContext());
                startActivity(i);
            }
        });
        mmnBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mmnPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("mmn", true);
                    editor.apply();
                    Log.d(TAG, "Mr And Mrs North Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Mr And Mrs North", getApplicationContext());
                startActivity(i);
            }
        });
        qpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!qpPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("qp", true);
                    editor.apply();
                    Log.d(TAG, "Quiet Please Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Quiet Please", getApplicationContext());
                startActivity(i);
            }
        });
        hlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!hlPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("hl", true);
                    editor.apply();
                    Log.d(TAG, "Harry Lime Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("The Lives of Harry Lime", getApplicationContext());
                startActivity(i);
            }
        });
        ssBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!ssPressed) {
                    editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("ss", true);
                    editor.apply();
                    Log.d(TAG, "Suspense Pressed!");
                }

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Suspense", getApplicationContext());
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