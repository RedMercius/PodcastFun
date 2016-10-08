/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.RuffinApps.johnnie.oldtimeradio;

/////////////////////////////////////////////////////////////////////////////
//
/// @class ImageControl
//
/// @brief ImageControl class controls image items
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

public class ImageControl {

    Integer[] imageButtonList = {
            R.mipmap.play50,
            R.mipmap.pause50,
            R.drawable.ic_stop,
            R.drawable.ic_close,
            R.mipmap.ic_download,
            R.drawable.ic_download_0,
            R.drawable.ic_info,
            R.drawable.ic_delete,
            R.mipmap.ic_stream,
            R.mipmap.start50,
            R.mipmap.end50,
            R.drawable.lowvolume50,
            R.drawable.highvolume50,
            R.drawable.mute50,
            R.drawable.menu2
    };

    public Integer[] getImageButtonList(){
     return imageButtonList;
    }
}
