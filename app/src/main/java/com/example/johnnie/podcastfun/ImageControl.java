/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.example.johnnie.podcastfun;

/**
 * Created by johnnie on 10/9/2015.
 */
public class ImageControl {

    Integer[] imageButtonList = {
            R.drawable.ic_play,
            R.drawable.ic_pause,
            R.drawable.ic_stop,
            R.drawable.ic_close,
            R.drawable.ic_download,
            R.drawable.ic_download_0,
            R.drawable.ic_info,
            R.drawable.ic_delete,
            R.drawable.ic_stream
    };

    Integer[] imageButtonListStop= {
            R.drawable.ic_close,
            R.drawable.ic_close,
            R.drawable.ic_close
    };

    public Integer[] getImageButtonList(){
     return imageButtonList;
    }

    public Integer[] getImageButtonListStop() {
        return imageButtonListStop;
    }

}
