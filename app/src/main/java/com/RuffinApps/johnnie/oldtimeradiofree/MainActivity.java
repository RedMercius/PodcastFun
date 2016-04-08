/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/*import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;*/


public class MainActivity extends AppCompatActivity {

    Button comedyButton;
    Button scifiButton;
    Button thrillerButton;

   /* private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgo/IdsqOpiQUVftNMPUJeHQ1JiaNKPD7b1ygx5Lp2XNpfv2NIqKWDftZFW7721kYKdOrG3YJFl1RK/pPQRJstH30OMEnTSSX+1VT3nauMX36GYuJFj4n7HKZcVZ5rndJaDIiBK8qO7xPNaGlJVMMWjnYLlzDpvGCAeNGD+vWc8NAvBrGaE6gWD0/rZByTjjx3RIxp6ZR9jHJEq5zS4ZN019rGQM5WyAxPTz/CL1G6Migojp0pWl3xuzBJgKg5Q0lMriq9JVMR15wL0MLd8c28+o7gy9OVz2e3arvjqXZWN8pV8+dzEStoMcuO07OFwTNoDA0VDvESi5Rp6xoM3LevQIDAQAB";
    private static final byte[] SALT = new byte[] { 99,77,75,29,18,57,73,63,72,32,01,54,54,04,69,29,38,28,56,06 };

    private LicenseChecker mChecker;
    private LicenseCheckerCallback mLicenseCheckerCallback;*/
    private SecurePreferences mpreferences;
    boolean licensed;
    boolean checkingLicense;
    boolean didCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** Start License Checking ***/
      /*  didCheck = false;
        mpreferences = new SecurePreferences(this, "jotr-preferences", "Loki13026044", true);

        String user = mpreferences.getString("LicenseCheck");

        if (user != null) {
            if (user.equals("true")) {
            didCheck = true;
            }
        }

        if (!didCheck) {
            mLicenseCheckerCallback = new MyLicenseCheckerCallback();

            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            mChecker = new LicenseChecker(getApplicationContext(), new ServerManagedPolicy(this, new AESObfuscator(SALT,
                    getPackageName(), deviceId)), BASE64_PUBLIC_KEY);


            mChecker.checkAccess(mLicenseCheckerCallback);

            Toast.makeText(this, "Checking application license...", Toast.LENGTH_SHORT).show();
            doCheck();
        }*/
        /*** End License Checking ***/

        comedyButton = (Button) findViewById(R.id.comedyButton);
        scifiButton = (Button) findViewById(R.id.scifiButton);
        thrillerButton = (Button) findViewById(R.id.thrillerButton);



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

                // TODO: Add popup for full version
            }
        });

        thrillerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent i = new Intent(MainActivity.this, ThrillerActivity.class);
                startActivity(i);

                // TODO: Add popup for full version
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

    /*private void doCheck() {

        didCheck = false;
        checkingLicense = true;
        setProgressBarIndeterminateVisibility(true);

        mChecker.checkAccess(mLicenseCheckerCallback);
    }


    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {

        @Override
        public void allow(int reason) {
            // TODO Auto-generated method stub
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            Log.i("License","Accepted!");

            //You can do other things here, like saving the licensed status to a
            //SharedPreference so the app only has to check the license once.

            licensed = true;
            checkingLicense = false;
            didCheck = true;

            // Put (all puts are automatically committed)
            mpreferences.put("LicenseCheck", "true");
        }

        @SuppressWarnings("deprecation")
        @Override
        public void dontAllow(int reason) {
            // TODO Auto-generated method stub
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            Log.i("License","Denied!");
            Log.i("License","Reason for denial: "+reason);

            //You can do other things here, like saving the licensed status to a
            //SharedPreference so the app only has to check the license once.

            licensed = false;
            checkingLicense = false;
            didCheck = true;

            showDialog(0);

        }

        @SuppressWarnings("deprecation")
        @Override
        public void applicationError(int reason) {
            // TODO Auto-generated method stub
            Log.i("License", "Error: " + reason);
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            licensed = true;
            checkingLicense = false;
            didCheck = false;

            showDialog(0);
        }


    }

    protected Dialog onCreateDialog(int id) {
        // We have only one dialog.
        return new AlertDialog.Builder(this)
                .setTitle("UNLICENSED APPLICATION DIALOG TITLE")
                .setMessage("This application is not licensed, please buy it from the play store.")
                .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                                "http://market.android.com/details?id=" + getPackageName()));
                        startActivity(marketIntent);
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton("Re-Check", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        doCheck();
                    }
                })

                .setCancelable(false)
                .setOnKeyListener(new DialogInterface.OnKeyListener(){
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        Log.i("License", "Key Listener");
                        finish();
                        return true;
                    }
                })
                .create();

    }*/
}
