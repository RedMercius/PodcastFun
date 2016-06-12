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
import android.view.View;
import android.widget.ImageButton;

public class WesternActivity extends AppCompatActivity {

    ImageButton hopalongBtn;
    ImageButton ftLaramieBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_western);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        hopalongBtn = (ImageButton) findViewById(R.id.hopalongBtn);
        ftLaramieBtn = (ImageButton) findViewById(R.id.ftLaramieBtn);

        hopalongBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Hopalong Cassidy");
                startActivity(i);
            }
        });

        ftLaramieBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(WesternActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Fort Laramie");
                startActivity(i);
            }
        });
    }
}
