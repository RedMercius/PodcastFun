package com.RuffinApps.johnnie.oldtimeradio;

import android.content.Context;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;

/**
 * Created by johnnie on 10/27/2017.
 */

public class CurrentArtist {
    private String artist;
    private String title;
    private String file;
    private String lastTitle;
    private String category;
    private int image;
    private int duration;
    private RadioTitle radioList = null;
    private String TAG = "Current Artist: ";
    private boolean isPlaying;
    private boolean isComplete;
    private boolean isAd;

    private MediaDescriptionCompat lastDescription;

    //a private constructor so no instances can be made outside this class
    private CurrentArtist() {}

     private static CurrentArtist instance = null;
    //Everytime you need an instance, call this
    //synchronized to make the call thread-safe
    public static synchronized CurrentArtist getInstance() {

        if(instance == null)
            instance = new CurrentArtist();

        return instance;
    }

    //Initialize this or any other variables in probably the Application class
    public void init(Context context) {
        this.artist = null;
        this.file = null;
        this.title = null;
        this.lastTitle = null;
        this.lastDescription = null;
        this.image = 0;
        this.radioList = RadioTitle.getInstance();
        this.isPlaying = false;
        this.isComplete = false;
        this.category = null;
        this.isAd = false;
    }

    // set and get current artist
    public void setCurrentArtist(String newArtist, Context ct)
    {
        this.artist = newArtist;
        setPlayPic(ct);
    }

    public String getCurrentArtist()
    {
        return this.artist;
    }

    // set and get current title
    public void setCurrentTitle(String newTitle) {this.title = newTitle; }
    public String getCurrentTitle() { return this.title; }

    // set and get current file
    public void setCurrentFile(String newFile) {this.file = newFile;
    // Log.d(TAG, "setCurrentFile: " + newFile);
    }

    public String getCurrentFile() {return this.file; }

    // set and get current duration
    public void setCurrentDuration(int newDuration) {this.duration = newDuration; }
    public int getCurrentDuration() {return this.duration; }

    public void setCurrentImage (int newImage) {this.image = newImage; }
    public int getCurrentImage () { return this.image; }

    public void setLastTitle(String newlastTitle) {this.lastTitle = newlastTitle; }
    public String getLastTitle() {return this.lastTitle; }

    public void setCurrentCategory (String newCategory) {this.category = newCategory; }
    public String getCurrentCategory() { return this.category; }

    public void setLastDescription(MediaDescriptionCompat newDesc) {this.lastDescription = newDesc;}
    public MediaDescriptionCompat getLastDescription() { return this.lastDescription; }

    public void setPlaying(boolean playing) { isPlaying = playing; }
    public boolean isPlaying() {return this.isPlaying; }

    public void setComplete(boolean complete) { isComplete = complete; }
    public boolean isComplete() {return this.isComplete; }

    public void setAd(boolean ad) { isAd = ad;}
    public boolean isAd() {return this.isAd;}

    public String[] getRadioTitles(String artist)
    {
        Log.d(TAG, "Artist: " + this.artist);
        switch (this.artist)
        {
            case "Burns And Allen":
            {
                return this.radioList.burnsAllenTitle;
            }

            case "Fibber McGee And Molly":
            {
                return this.radioList.fibberTitle;
            }

            case "Martin And Lewis":
            {
                return this.radioList.martinAndLewisTitle;
            }

            case "The Great GilderSleeves":
            {
                Log.d(TAG, "gilderSleeves: " + CurrentArtist.getInstance().radioList.gilderSleevesTitle[0]);
                return CurrentArtist.getInstance().radioList.gilderSleevesTitle;
            }

            case "Jack Benny":
            {
                return this.radioList.jackBennyTitle;
            }

            case "Bob Hope":
            {
                return this.radioList.bobHopeTitle;
            }

            case "XMinus1":
            {
                return this.radioList.xminusTitle;
            }

            case "Inner Sanctum":
            {
                return this.radioList.innerSanctumTitle;
            }

            case "Dimension X":
            {
                return this.radioList.dimensionXTitle;
            }

            case "Night Beat":
            {
                return this.radioList.nightBeatTitle;
            }

            case "Speed Gibson":
            {
                return this.radioList.speedTitle;
            }

            case "The Whistler":
            {
                return this.radioList.whistlerTitle;
            }

            case "Hopalong Cassidy":
            {
                return this.radioList.hopalongTitle;
            }

            case "Fort Laramie":
            {
                return this.radioList.ftLaramieTitle;
            }

            case "Our Miss Brooks":
            {
                return this.radioList.missBrooksTitle;
            }

            case "Father Knows Best":
            {
                return this.radioList.fatherKnowsBestTitle;
            }

            case "Lone Ranger":
            {
                return this.radioList.loneRangerTitle;
            }

            case "Ozzie And Harriet":
            {
                return this.radioList.ozzieAndHarrietTitle;
            }

            case "The Life Of Riley":
            {
                return this.radioList.lifeOfRileyTitle;
            }

            case "Flash Gordon":
            {
                return this.radioList.flashGordonTitle;
            }

            case "SciFi Radio":
            {
                return this.radioList.sciFiRadioTitle;
            }

            case "The Green Hornet":
            {
                return this.radioList.greenHornetTitle;
            }

            case "Adventures By Morse":
            {
                return this.radioList.adventureByMorseTitle;
            }

            case "Adventures of Dick Cole":
            {
                return this.radioList.adventureofDickColeTitle;
            }

            case "Blondie":
            {
                return this.radioList.blondieTitle;
            }

            case "Bold Venture":
            {
                return this.radioList.boldVentureTitle;
            }

            case "Boston Blackie":
            {
                return this.radioList.bostonBlackieTitle;
            }

            case "CBS Radio Mystery Theater":
            {
                return this.radioList.cbsRadioTitle;
            }

            case "Dangerous Assignment":
            {
                return this.radioList.dangerousAssignmentTitle;
            }

            case "Duffys Tavern":
            {
                return this.radioList.duffysTavernTitle;
            }

            case "Mr And Mrs North":
            {
                return this.radioList.mrAndMrsNorthTitle;
            }

            case "Quiet Please":
            {
                return this.radioList.quietPleaseTitle;
            }

            case "Suspense":
            {
               return this.radioList.suspenseTitle;
            }

            case "The Lives of Harry Lime":
            {
                return this.radioList.harryLimeTitle;
            }

            case "Pat O":
            {
                return this.radioList.PatOTitle;
            }

            case "Have Gun Will Travel":
            {
                return this.radioList.HaveGunTitle;
            }

            default: {
                return null;
            }
        }
    }

    public String getArtistUrl() {
        String url;
        switch (this.artist) {
            case "Burns And Allen": {
                url = "http://www.ruffinapps.com/Audio/BurnsAllan/";
                break;
            }

            case "Fibber McGee And Molly": {
                url = "http://www.ruffinapps.com/Audio/FibberMcGeeandMolly/";
                break;
            }

            case "Martin And Lewis": {
                url = "http://www.johnnieruffin.com/Audio/MartinAndLewis_OldTimeRadio/";
                break;
            }

            case "The Great GilderSleeves": {
                url = "http://www.ruffinapps.com/Audio/GilderSleeves/";
                break;
            }

            case "Jack Benny":
            {
                url = "http://www.johnnieruffin.com/Audio/JackBenny/";
                break;
            }

            case "Bob Hope":
            {
                url = "http://www.johnnieruffin.com/Audio/BobHope/";
                break;
            }

            case "XMinus1": {
                url = "http://www.johnnieruffin.com/Audio/XMinus1/";
                break;
            }

            case "Inner Sanctum": {
                url = "http://www.johnnieruffin.com/Audio/InnerSanctum/";
                break;
            }

            case "Dimension X": {
                url = "http://www.johnnieruffin.com/Audio/DimensionX/";
                break;
            }

            case "Night Beat": {
                url = "http://www.johnnieruffin.com/Audio/NightBeat/";
                break;
            }

            case "Speed Gibson": {
                url = "http://www.johnnieruffin.com/Audio/Speed/";
                break;
            }

            case "The Whistler": {
                url = "http://www.johnnieruffin.com/Audio/TheWhistler/";
                break;
            }

            case "Hopalong Cassidy":
            {
                url = "http://www.johnnieruffin.com/Audio/Hopalong/";
                break;
            }

            case "Fort Laramie":
            {
                url = "http://www.johnnieruffin.com/Audio/FtLaramie/";
                break;
            }

            case "Our Miss Brooks":
            {
                url = "http://www.ruffinapps.com/Audio/Brooks/";
                break;
            }
            case "Father Knows Best":
            {
                url = "http://www.ruffinapps.com/Audio/FatherKnowsBest/";
                break;
            }
            case "Lone Ranger":
            {
                url = "http://www.ruffinapps.com/Audio/LoneRanger/";
                break;
            }

            case "Ozzie And Harriet":
            {
                url = "http://www.ruffinapps.com/Audio/OzzieHarriet/";
                break;
            }

            case "The Life Of Riley":
            {
                url = "http://www.ruffinapps.com/Audio/TheLifeOfRiley/";
                break;
            }

            case "Flash Gordon":
            {
                url = "http://www.ruffinapps.com/Audio/FlashGordon1935/";
                break;
            }

            case "SciFi Radio":
            {
                url = "http://www.ruffinapps.com/Audio/scifiRadio/";
                break;
            }

            case "The Green Hornet":
            {
                url = "http://www.ruffinapps.com/Audio/TheGreenHornet/";
                break;
            }

            case "Adventures By Morse":
            {
                url = "http://www.ruffinapps.com/Audio/AdventuresByMorse/";
                break;
            }

            case "Adventures of Dick Cole":
            {
                url = "http://www.ruffinapps.com/Audio/AdventuresofDickCole/";
                break;
            }

            case "Blondie":
            {
                url = "http://www.ruffinapps.com/Audio/Blondie/";
                break;
            }

            case "Bold Venture":
            {
                url = "http://www.ruffinapps.com/Audio/BoldVenture/";
                break;
            }

            case "Boston Blackie":
            {
                url = "http://www.ruffinapps.com/Audio/BostonBlackie/";
                break;
            }

            case "CBS Radio Mystery Theater":
            {
                url = "http://www.ruffinapps.com/Audio/cbs_radio_mystery_theater/";
                break;
            }

            case "Dangerous Assignment":
            {
                url = "http://www.ruffinapps.com/Audio/DangerousAssignment/";
                break;
            }

            case "Duffys Tavern":
            {
                url = "http://www.ruffinapps.com/Audio/DuffysTavern/";
                break;
            }

            case "Mr And Mrs North":
            {
                url = "http://www.ruffinapps.com/Audio/MrandMrsNorth/";
                break;
            }

            case "Quiet Please":
            {
                url = "http://www.ruffinapps.com/Audio/QuietPlease/";
                break;
            }

            case "Suspense":
            {
                url = "http://www.ruffinapps.com/Audio/suspense/";
                break;
            }

            case "The Lives of Harry Lime":
            {
                url = "http://www.ruffinapps.com/Audio/TheLivesOfHarryLime/";
                break;
            }

            case "Have Gun Will Travel":
            {
                url = "http://www.ruffinapps.com/Audio/HaveGunWillTravel/";
                break;
            }

            case "Pat O":
            {
                url = "http://www.ruffinapps.com/Audio/PatO/";
                break;
            }

            default: {
                Log.d(TAG, "Unknown artists: " + this.artist);
                url = null;
                break;
            }
        }
        // Log.d(TAG, "url: " + url);
        return url;
    }

    private void setPlayPic(Context ct) {
        switch (this.artist) {
            case "Burns And Allen": {
                setImageId("burnsandallen", ct);
                break;
            }

            case "Fibber McGee And Molly": {
                setImageId("fibber_and_molly_", ct);
                break;
            }

            case "Martin And Lewis": {
                setImageId("lewis_and_martin", ct);
                break;
            }

            case "The Great GilderSleeves": {
                setImageId("greatgildersleeve", ct);
                break;
            }

            case "XMinus1": {
                setImageId("xminusone", ct);
                break;
            }

            case "Inner Sanctum": {
                setImageId("innersanctum0", ct);
                break;
            }

            case "Dimension X": {
                setImageId("dimension_x", ct);
                break;
            }

            case "Night Beat": {
                setImageId("nightbeat", ct);
                break;
            }

            case "Speed Gibson": {
                setImageId("sgimage", ct);
                break;
            }

            case "The Whistler": {
                setImageId("thewhistler", ct);
                break;
            }

            case "Jack Benny": {
                setImageId("jackbenny_fixed", ct);
                break;
            }

            case "Bob Hope": {
                setImageId("bob_hope_1950_0_fixed_0", ct);
                break;
            }

            case "Hopalong Cassidy": {
                setImageId("hopalongcassidy", ct);
                break;
            }

            case "Fort Laramie": {
                setImageId("ftlaramie", ct);
                break;
            }

            case "Our Miss Brooks": {
                setImageId("ourmissbrooks", ct);
                break;
            }

            case "Father Knows Best": {
                setImageId("fatherknowsbest", ct);
                break;
            }

            case "Lone Ranger": {
                setImageId("loneranger", ct);
                break;
            }

            case "Ozzie And Harriet": {
                setImageId("ozzieandharriet", ct);
                break;
            }

            case "The Life Of Riley": {
                setImageId("lifeofriley", ct);
                break;
            }
            case "Flash Gordon": {
                setImageId("flashgordon", ct);
                break;
            }
            case "SciFi Radio": {
                setImageId("scifiradio", ct);
                break;
            }
            case "The Green Hornet": {
                setImageId("greenhornet", ct);
                break;
            }
            case "Adventures By Morse": {
                setImageId("adbm", ct);
                break;
            }
            case "Adventures of Dick Cole": {
                setImageId("adc", ct);
                break;
            }
            case "Blondie": {
                setImageId("blondie", ct);
                break;
            }
            case "Bold Venture": {
                setImageId("boldventure", ct);
                break;
            }
            case "Boston Blackie": {
                setImageId("bostonblackie", ct);
                break;
            }
            case "CBS Radio Mystery Theater": {
                setImageId("cbsradio", ct);
                break;
            }

            case "Dangerous Assignment": {
                setImageId("dangerousassignment", ct);
                break;
            }
            case "Duffys Tavern": {
                setImageId("duffystavern", ct);
                break;
            }
            case "Mr And Mrs North": {
                setImageId("mrmrsnorth", ct);
                break;
            }

            case "Quiet Please": {
                setImageId("quietplease", ct);
                break;
            }
            case "Suspense": {
                setImageId("suspense", ct);
                break;
            }
            case "The Lives of Harry Lime": {
                setImageId("harrylime", ct);
                break;
            }
            case "Pat O":
            {
                setImageId("pato", ct);
                break;
            }
            case "Have Gun Will Travel": {
                setImageId("havegun", ct);
                break;
            }

            default: {
                setImageId("burnsandallen", ct);
                break;
            }
        }
    }

    private void setImageId(String imageName, Context ct)
    {
        //to retrieve image in res/drawable and set image in ImageView
        int resID = ct.getResources().getIdentifier(imageName, "mipmap", "com.RuffinApps.johnnie.oldtimeradio");
        CurrentArtist.getInstance().setCurrentImage(resID);
    }
    
    public String getMediaFile(String Title)
    {
        // radioList.initTitles();

        String MediaFile = null;

        switch (this.artist) {
            case "Burns And Allen": {
                for (String mediaFile : radioList.getBaMap().keySet()) {
                    if (radioList.getBaMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Fibber McGee And Molly": {
                for (String mediaFile : radioList.getFbMap().keySet()) {
                    if (radioList.getFbMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Martin And Lewis": {
                for (String mediaFile : radioList.getMlMap().keySet()) {
                    if (radioList.getMlMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Great GilderSleeves": {
                for (String mediaFile : radioList.getGlMap().keySet()) {
                    if (radioList.getGlMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Jack Benny": {
                for (String mediaFile : radioList.getJbMap().keySet()) {
                    if (radioList.getJbMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Bob Hope": {
                for (String mediaFile : radioList.getBhMap().keySet()) {
                    if (radioList.getBhMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "XMinus1": {
                for (String mediaFile : radioList.getXMMap().keySet()) {
                    if (radioList.getXMMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Inner Sanctum": {
                for (String mediaFile : radioList.getIsMap().keySet()) {
                    if (radioList.getIsMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Dimension X": {
                for (String mediaFile : radioList.getDxMap().keySet()) {
                    if (radioList.getDxMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Night Beat": {
                for (String mediaFile : radioList.getNbMap().keySet()) {
                    if (radioList.getNbMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Speed Gibson": {
                for (String mediaFile : radioList.getSgMap().keySet()) {
                    if (radioList.getSgMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Whistler": {
                for (String mediaFile : radioList.getWsMap().keySet()) {
                    if (radioList.getWsMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Hopalong Cassidy": {
                for (String mediaFile : radioList.getHcMap().keySet()) {
                    if (radioList.getHcMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Fort Laramie": {
                for (String mediaFile : radioList.getFlMap().keySet()) {
                    if (radioList.getFlMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Our Miss Brooks": {
                for (String mediaFile : radioList.getMbMap().keySet()) {
                    if (radioList.getMbMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Father Knows Best": {
                for (String mediaFile : radioList.getFkMap().keySet()) {
                    if (radioList.getFkMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Lone Ranger": {
                for (String mediaFile : radioList.getLrMap().keySet()) {
                    if (radioList.getLrMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Pat O": {
                for (String mediaFile : radioList.getPoMap().keySet()) {
                    if (radioList.getPoMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Ozzie And Harriet": {
                for (String mediaFile : radioList.getOhMap().keySet()) {
                    if (radioList.getOhMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Life Of Riley": {
                for (String mediaFile : radioList.getOrMap().keySet()) {
                    if (radioList.getOrMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Blondie": {
                for (String mediaFile : radioList.getBlMap().keySet()) {
                    if (radioList.getBlMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Flash Gordon": {
                for (String mediaFile : radioList.getFgMap().keySet()) {
                    if (radioList.getFgMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "SciFi Radio": {
                for (String mediaFile : radioList.getSrMap().keySet()) {
                    if (radioList.getSrMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Green Hornet": {
                for (String mediaFile : radioList.getGhMap().keySet()) {
                    if (radioList.getGhMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Adventures By Morse": {
                for (String mediaFile : radioList.getAbmMap().keySet()) {
                    if (radioList.getAbmMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Adventures of Dick Cole": {
                for (String mediaFile : radioList.getAdcMap().keySet()) {
                    if (radioList.getAdcMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Bold Venture": {
                for (String mediaFile : radioList.getBvMap().keySet()) {
                    if (radioList.getBvMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Boston Blackie": {
                for (String mediaFile : radioList.getBbMap().keySet()) {
                    if (radioList.getBbMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "CBS Radio Mystery Theater": {
                for (String mediaFile : radioList.getCbsMap().keySet()) {
                    if (radioList.getCbsMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Dangerous Assignment": {
                for (String mediaFile : radioList.getDaMap().keySet()) {
                    if (radioList.getDaMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Duffys Tavern": {
                for (String mediaFile : radioList.getDtMap().keySet()) {
                    if (radioList.getDtMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Mrs And Mrs North": {
                for (String mediaFile : radioList.getMmnMap().keySet()) {
                    if (radioList.getMmnMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Quiet Please": {
                for (String mediaFile : radioList.getQpMap().keySet()) {
                    if (radioList.getQpMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Suspense": {
                for (String mediaFile : radioList.getSsMap().keySet()) {
                    if (radioList.getSsMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "The Lives of Harry Lime": {
                for (String mediaFile : radioList.getHlMap().keySet()) {
                    if (radioList.getHlMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            case "Have Gun Will Travel": {
                for (String mediaFile : radioList.getHgMap().keySet()) {
                    if (radioList.getHgMap().get(mediaFile).equals(Title)) {
                        MediaFile = mediaFile;
                    }
                }
                break;
            }

            default:
            {
                Log.d(TAG, "Unknown show!!!");
                break;
            }
        }
        return MediaFile;
    }
}
