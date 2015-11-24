/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

//////////////////////////////////////////////////////////////////////////////////////
//
///  @class DownloadControl
//
///  @author Johnnie
//
///  @brief A class to control the Download of files
//
///  @created 10/10/2015
//
//////////////////////////////////////////////////////////////////////////////////////
public class DownloadControl extends Activity {
    private long enqueue;
    public DownloadManager dm;
    private String webPath;
    private String TAG = "Download Control: ";
    private boolean m_downloadSpaceRemaining;
    private long m_mediaFileSize;

   /* public boolean isExternalStorage(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        return mExternalStorageAvailable;
    }*/

    /** Called when the activity is first created. */
    public DownloadControl(Activity context) {
        webPath = "http://www.JohnnieRuffin.com/audio/";
        Log.d(TAG, "DownloadControl -- constructor");

        m_mediaFileSize = 0;
        m_downloadSpaceRemaining = false;
    }

    public void setWebPath(String url)
    {
        webPath = url;
    }

    public void deleteMedia(String filename)
    {
        String filePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/" + filename);
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
        }
    }

    public long getDlId()
    {
    return enqueue;
    }

    public void downloadFile(String filename, Activity context) {

        String customfilePath =(webPath + filename);
        dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Request request = new Request(
                Uri.parse(customfilePath)).setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, filename);
        enqueue = dm.enqueue(request);

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                  Thread.currentThread().sleep(1000);
                  isSpaceAvailableForDownload();
                  Thread.currentThread().interrupt();
              }
              catch(InterruptedException e)
              {
                  Log.d(TAG, "Interrupted Exception: " + e);
              }

            }
        });

        thread.start();*/

        Log.e(TAG, "downloadFile: " + filename);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {

            try {
                getFileSize();
            }
            catch (IllegalStateException e)
            {
                Log.d(TAG, "IllegalStateException_run: " + e);
            }
        }
    };

    private long getFileSize() {
        int total_size = 0;
        try {
            Cursor c = dm.query(new DownloadManager.Query().setFilterById(enqueue));

            if (c.moveToFirst()) {
                total_size = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            }

            c.close();
        } catch (IllegalStateException e)
        {
            Log.d(TAG, "IllegalStateException_runProgress: " + e);
        }

        Log.d(TAG, "File Size_1: " + total_size);
        return total_size;
    }

    private long getAvailableSpace()
    {

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
        // long megAvailable = bytesAvailable / 1048576;

        return bytesAvailable;
    }

    private boolean isSpaceAvailableForDownload()
    {
        boolean isSpaceAvailable = false;
        if ((getAvailableSpace() - getFileSize()) > 0)
        {
            isSpaceAvailable = true;
        }

        Log.d(TAG, "Space Available: " + (getAvailableSpace() - getFileSize()));
        return isSpaceAvailable;
    }
}
