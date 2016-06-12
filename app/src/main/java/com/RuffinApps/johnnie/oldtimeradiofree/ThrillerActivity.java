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

public class ThrillerActivity extends AppCompatActivity {

    ImageButton nbBtn;
    ImageButton sgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thriller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nbBtn = (ImageButton) findViewById(R.id.nbBtn);
        sgBtn = (ImageButton) findViewById(R.id.sgBtn);

        nbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Night Beat");
                startActivity(i);
            }
        });

        sgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(ThrillerActivity.this, SelectActivity.class);
                i.putExtra("Selection", "Speed");
                startActivity(i);
            }
        });
    }

}
