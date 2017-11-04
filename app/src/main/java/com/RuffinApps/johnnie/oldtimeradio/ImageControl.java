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
            R.mipmap.ic_stop,
            R.mipmap.ic_close,
            R.mipmap.ic_download,
            R.mipmap.ic_download_0,
            R.mipmap.ic_info,
            R.mipmap.ic_delete,
            R.mipmap.ic_stream,
            R.mipmap.start50,
            R.mipmap.end50,
            R.mipmap.lowvolume50,
            R.mipmap.highvolume50,
            R.mipmap.mute50,
            R.mipmap.menu2,
            R.mipmap.menu5_horizontal
    };

    public Integer[] getImageButtonList(){
     return imageButtonList;
    }
}
