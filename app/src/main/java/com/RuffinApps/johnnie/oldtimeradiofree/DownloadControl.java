/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradiofree;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
// import android.util.Log;

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
public class DownloadControl {
    private long enqueue;
    public DownloadManager dm;
    private String webPath;
    //private String TAG = "Download Control: ";
    private Activity context;

    /** Called when the activity is first created. */
    public DownloadControl(Activity context) {
        this.context = context;
        webPath = "http://www.JohnnieRuffin.com/audio/";
    }

    public void setWebPath(String url)
    {
        webPath = url;
    }

    public boolean deleteMedia(String filename)
    {
        boolean deleted = false;
        String filePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/" + filename);
        File file = new File(filePath);
        //Log.d(TAG, "Delete File: " + filePath);
        if (file.exists()) {
            deleted = file.delete();
        }
        return deleted;
    }

    public long getDlId()
    {
    return enqueue;
    }

    public void downloadFile(String filename) {

        String customfilePath =(webPath + filename);
        dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(
                Uri.parse(customfilePath)).setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, filename);
        enqueue = dm.enqueue(request);
    }
}
