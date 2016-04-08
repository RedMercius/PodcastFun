/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class ComedyActivity extends AppCompatActivity {

    ImageButton baBtn;
    ImageButton fbBtn;
    ImageButton mlBtn;
    ImageButton gilBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        baBtn = (ImageButton) findViewById(R.id.imageBtn1);
        fbBtn = (ImageButton) findViewById(R.id.fbBtn);
        mlBtn = (ImageButton) findViewById(R.id.mlBtn);
        gilBtn = (ImageButton) findViewById(R.id.gilBtn);

        baBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Burns And Allen");
                startActivity(i);
            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Fibber McGee And Molly");
                startActivity(i);
                // TODO: Add popup for full version
            }
        });

        mlBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Martin And Lewis");
                startActivity(i);
                // TODO: Add popup for full version
            }
        });

        gilBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ComedyActivity.this, SelectActivity.class);
                i.putExtra("Selection", "The Great GilderSleeves");
                startActivity(i);
                // TODO: Add popup for full version
            }
        });
    }
}
