/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

public class ComedyActivity extends AppCompatActivity {

    ImageButton baBtn;
    ImageButton fbBtn;
    ImageButton mlBtn;
    ImageButton gilBtn;
    ImageButton jbBtn;
    ImageButton bhBtn;
    ImageButton mbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        baBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Burns And Allen");
                startActivity(i);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Fibber McGee And Molly");
                startActivity(i);
            }
        });

        mlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Martin And Lewis");
                startActivity(i);
            }
        });

        gilBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("The Great GilderSleeves");
                startActivity(i);
            }
        });

        jbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Jack Benny");
                startActivity(i);
            }
        });

        bhBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Bob Hope");
                startActivity(i);
            }
        });

        mbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                CurrentArtist.getInstance().setCurrentArtist("Our Miss Brooks");
                startActivity(i);
            }
        });
    }

    // TODO: Handle hard input button presses or joystick button presses.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        boolean handled = false;

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:
                // ... handle selections
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // ... handle left action
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // ... handle right action
                handled = true;
                break;
        }
        return handled || super.onKeyDown(keyCode, event);
    }
}
