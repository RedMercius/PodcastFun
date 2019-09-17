package com.RuffinApps.johnnie.oldtimeradio;
/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

/////////////////////////////////////////////////////////////////////////////
//
/// @class RadioTitle
//
/// @brief A Class that controls radio titles
//
/// @author Johnnie Ruffin
//
////////////////////////////////////////////////////////////////////////////

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import java.util.HashMap;

public class RadioTitle {

    // private String TAG = "RadioTitle: ";

    // comedy maps
    private HashMap<String, String> baMap = null;
    private HashMap<String, String> fbMap = null;
    private HashMap<String, String> mlMap = null;
    private HashMap<String, String> glMap = null;
    private HashMap<String, String> jbMap = null;
    private HashMap<String, String> bhMap = null;
    private HashMap<String, String> mbMap = null;
    private HashMap<String, String> fkMap = null;
    private HashMap<String, String> ohMap = null;
    private HashMap<String, String> orMap = null;

    // Sci-fi maps
    private HashMap<String, String> xmMap = null;
    private HashMap<String, String> isMap = null;
    private HashMap<String, String> dxMap = null;
    private HashMap<String, String> fgMap = null;
    private HashMap<String, String> srMap = null;
    private HashMap<String, String> ghMap = null;

    // Thriller Maps
    private HashMap<String, String> nbMap = null;
    private HashMap<String, String> sgMap = null;
    private HashMap<String, String> wsMap = null;
    private HashMap<String, String> abmMap = null;
    private HashMap<String, String> adcMap = null;
    private HashMap<String, String> blMap = null;
    private HashMap<String, String> bvMap = null;
    private HashMap<String, String> bbMap = null;
    private HashMap<String, String> cbsMap = null;
    private HashMap<String, String> daMap = null;
    private HashMap<String, String> dtMap = null;
    private HashMap<String, String> mmnMap = null;
    private HashMap<String, String> qpMap = null;
    private HashMap<String, String> ssMap = null;
    private HashMap<String, String> hlMap = null;

    // Western Maps
    private HashMap<String, String> hcMap = null;
    private HashMap<String, String> flMap = null;
    private HashMap<String, String> lrMap = null;
    private HashMap<String, String> poMap = null;
    private HashMap<String, String> hgMap = null;
    
    private String TAG = "RadioTitle";
    
    private JSONNav jnav = null;

    public String[] burnsAllenTitle = null;
    public String[] burnsAllen = null;
    public String[] fibberTitle = null;
    public String[] fibber = null;
    public String[] jackBennyTitle = null;
    public String[] jackBenny = null;
    public String[] gilderSleevesTitle = null;
    public String[] gilderSleeves = null;
    public String[] martinAndLewisTitle = null;
    public String[] martinAndLewis = null;
    public String[] bobHopeTitle = null;
    public String[] bobHope = null;
    public String[] missBrooksTitle = null;
    public String[] missBrooks = null;
    public String[] fatherKnowsBestTitle = null;
    public String[] fatherKnowsBest = null;
    public String[] ozzieAndHarrietTitle = null;
    public String[] ozzieAndHarriet = null;
    public String[] lifeOfRiley = null;
    public String[] lifeOfRileyTitle = null;
    public String[] PatO = null;
    public String[] PatOTitle = null;
    public String[] flashGordon = null;
    public String[] flashGordonTitle = null;
    public String[] xminusTitle = null;
    public String[] xminus = null;
    public String[] innerSanctumTitle = null;
    public String[] innerSanctum = null;
    public String[] dimensionXTitle = null;
    public String[] dimensionX = null;
    public String[] sciFiRadio = null;
    public String[] sciFiRadioTitle = null;
    public String[] greenHornet = null;
    public String[] greenHornetTitle = null;
    public String[] nightBeat = null;
    public String[] nightBeatTitle = null;
    public String[] speed = null;
    public String[] speedTitle = null;
    public String[] whistler = null;
    public String[] whistlerTitle = null;
    public String[] adventureByMorse = null;
    public String[] adventureByMorseTitle = null;
    public String[] adventureofDickCole = null;
    public String[] adventureofDickColeTitle = null;
    public String[] blondie = null;
    public String[] blondieTitle = null;
    public String[] boldVenture = null;
    public String[] boldVentureTitle = null;
    public String[] bostonBlackie = null;
    public String[] bostonBlackieTitle = null;
    public String[] cbsRadio = null;
    public String[] cbsRadioTitle = null;
    public String[] dangerousAssignment = null;
    public String[] dangerousAssignmentTitle = null;
    public String[] duffysTavern = null;
    public String[] duffysTavernTitle = null;
    public String[] mrAndMrsNorth = null;
    public String[] mrAndMrsNorthTitle = null;
    public String[] quietPlease = null;
    public String[] quietPleaseTitle = null;
    public String[] suspense = null;
    public String[] suspenseTitle = null;
    public String[] harryLime = null;
    public String[] harryLimeTitle = null;
    public String[] hopalong = null;
    public String[] hopalongTitle = null;
    public String[] fortLaramie = null;
    public String[] ftLaramieTitle = null;
    public String[] loneRanger = null;
    public String[] loneRangerTitle = null;
    public String[] HaveGun = null;
    public String[] HaveGunTitle = null;

    private boolean comedyLoaded = false;
    private boolean thrillerLoaded = false;
    private boolean scifiLoaded = false;
    private boolean westernLoaded = false;

    private RadioTitle() {}

    private static RadioTitle instance = null;

    //Everytime you need an instance, call this
    //synchronized to make the call thread-safe
    public static synchronized RadioTitle getInstance() {

        if(instance == null)
            instance = new RadioTitle();

        return instance;
    }

    //Initialize this or any other variables in probably the Application class
    public void init(Context context) {

        this.jnav = JSONNav.getInstance();
    }

    public void loadJSONComedy(Context ct)
    {
        if(!comedyLoaded) {
            // load JSON file from assets
            jnav.loadJSONComedy(ct);
        }
    }

    public void loadJSONThriller(Context ct)
    {
        if(!thrillerLoaded) {
            // load JSON file from assets
            jnav.loadJSONThriller(ct);
        }
    }

    public void loadJSONSciFi(Context ct)
    {
        if (!scifiLoaded) {
            // load JSON file from assets
            jnav.loadJSONSciFi(ct);
        }
    }

    public void loadJSONWestern(Context ct)
    {
        if (!westernLoaded) {
            // load JSON file from assets
            jnav.loadJSONWestern(ct);
        }
    }

    public void updateComedyTitles()
    {
        if (comedyLoaded)
        {
            Log.d(TAG, "Comedy Already Loaded");
            return;
        }

        // comedy maps
        this.baMap = new HashMap<>();
        this.blMap = new HashMap<>();
        this.fbMap = new HashMap<>();
        this.mlMap = new HashMap<>();
        this.glMap = new HashMap<>();
        this.jbMap = new HashMap<>();
        this.bhMap = new HashMap<>();
        this.mbMap = new HashMap<>();
        this.fkMap = new HashMap<>();
        this.ohMap = new HashMap<>();
        this.orMap = new HashMap<>();

        Log.d(TAG, "Entering updateTitles");

        //  comedy
        burnsAllenTitle = jnav.getListOfEps(comedyCat[0],"Comedy");
        burnsAllen = new String[burnsAllenTitle.length];

        fibberTitle = jnav.getListOfEps(comedyCat[1], "Comedy");
        fibber = new String[fibberTitle.length];

        gilderSleevesTitle = jnav.getListOfEps(comedyCat[2], "Comedy");
        gilderSleeves = new String[gilderSleevesTitle.length];

        martinAndLewisTitle = jnav.getListOfEps(comedyCat[3], "Comedy");
        martinAndLewis = new String[martinAndLewisTitle.length];

        jackBennyTitle = jnav.getListOfEps(comedyCat[4], "Comedy");
        jackBenny = new String[jackBennyTitle.length];

        bobHopeTitle = jnav.getListOfEps(comedyCat[5], "Comedy");
        bobHope = new String[bobHopeTitle.length];

        missBrooksTitle = jnav.getListOfEps(comedyCat[6], "Comedy");
        missBrooks = new String[missBrooksTitle.length];

        fatherKnowsBestTitle = jnav.getListOfEps(comedyCat[7], "Comedy");
        fatherKnowsBest = new String[fatherKnowsBestTitle.length];

        ozzieAndHarrietTitle = jnav.getListOfEps(comedyCat[8], "Comedy");
        ozzieAndHarriet = new String[ozzieAndHarrietTitle.length];

        lifeOfRileyTitle = jnav.getListOfEps(comedyCat[9], "Comedy");
        lifeOfRiley = new String[lifeOfRileyTitle.length];

        blondieTitle = jnav.getListOfEps(comedyCat[10], "Comedy");
        blondie = new String[blondieTitle.length];

        for (int i = 0; i<burnsAllenTitle.length; i++)
        {
            burnsAllen[i] = jnav.getFilename(burnsAllenTitle[i], comedyCat[0], "Comedy");
            baMap.put(burnsAllen[i], burnsAllenTitle[i]);
        }

        for (int i = 0; i<fibberTitle.length; i++)
        {
            fibber[i] = jnav.getFilename(fibberTitle[i], comedyCat[1], "Comedy");
            fbMap.put(fibber[i], fibberTitle[i]);
        }

        for (int i = 0; i<gilderSleevesTitle.length; i++)
        {
            gilderSleeves[i] = jnav.getFilename(gilderSleevesTitle[i], comedyCat[2], "Comedy");
            glMap.put(gilderSleeves[i], gilderSleevesTitle[i]);
        }

        for (int i = 0; i<martinAndLewisTitle.length; i++)
        {
            martinAndLewis[i] = jnav.getFilename(martinAndLewisTitle[i], comedyCat[3], "Comedy");
            mlMap.put(martinAndLewis[i], martinAndLewisTitle[i]);
        }

        for (int i = 0; i<jackBennyTitle.length; i++)
        {
            jackBenny[i] = jnav.getFilename(jackBennyTitle[i], comedyCat[4], "Comedy");
            jbMap.put(jackBenny[i], jackBennyTitle[i]);
        }

        for (int i = 0; i<bobHopeTitle.length; i++)
        {
            bobHope[i] = jnav.getFilename(bobHopeTitle[i], comedyCat[5], "Comedy");
            bhMap.put(bobHope[i], bobHopeTitle[i]);
        }

        for (int i = 0; i<missBrooksTitle.length; i++)
        {
            missBrooks[i] = jnav.getFilename(missBrooksTitle[i], comedyCat[6], "Comedy");
            mbMap.put(missBrooks[i], missBrooksTitle[i]);
        }

        for (int i = 0; i<fatherKnowsBestTitle.length; i++)
        {
            fatherKnowsBest[i] = jnav.getFilename(fatherKnowsBestTitle[i], comedyCat[7], "Comedy");
            fkMap.put(fatherKnowsBest[i], fatherKnowsBestTitle[i]);
        }

        for (int i = 0; i<ozzieAndHarrietTitle.length; i++)
        {
            ozzieAndHarriet[i] = jnav.getFilename(ozzieAndHarrietTitle[i], comedyCat[8], "Comedy");
            ohMap.put(ozzieAndHarriet[i], ozzieAndHarrietTitle[i]);
        }

        for (int i = 0; i<lifeOfRileyTitle.length; i++)
        {
            lifeOfRiley[i] = jnav.getFilename(lifeOfRileyTitle[i], comedyCat[9], "Comedy");
            orMap.put(lifeOfRiley[i], lifeOfRileyTitle[i]);
        }

        for (int i = 0; i<blondieTitle.length; i++)
        {
            blondie[i] = jnav.getFilename(blondieTitle[i], comedyCat[10], "Comedy");
            blMap.put(blondie[i], blondieTitle[i]);
        }

        comedyLoaded = true;
    }

    public void updateThrillerTitles()
    {
        if (thrillerLoaded)
        {
            Log.d(TAG, "Thriller Already Loaded");
            return;
        }

        // Thriller Maps
        this.nbMap = new HashMap<>();
        this.sgMap = new HashMap<>();
        this.wsMap = new HashMap<>();
        this.abmMap = new HashMap<>();
        this.adcMap = new HashMap<>();
        this.bvMap = new HashMap<>();
        this.bbMap = new HashMap<>();
        this.cbsMap = new HashMap<>();
        this.daMap = new HashMap<>();
        this.dtMap = new HashMap<>();
        this.mmnMap = new HashMap<>();
        this.qpMap = new HashMap<>();
        this.ssMap = new HashMap<>();
        this.hlMap = new HashMap<>();

        // thriller
        nightBeatTitle = jnav.getListOfEps(thrillerCat[0], "Thriller");
        nightBeat = new String[nightBeatTitle.length];

        speedTitle = jnav.getListOfEps(thrillerCat[1], "Thriller");
        speed = new String[speedTitle.length];

        whistlerTitle = jnav.getListOfEps(thrillerCat[2], "Thriller");
        whistler = new String[whistlerTitle.length];

        adventureByMorseTitle = jnav.getListOfEps(thrillerCat[3], "Thriller");
        adventureByMorse = new String[adventureByMorseTitle.length];

        adventureofDickColeTitle = jnav.getListOfEps(thrillerCat[4], "Thriller");
        adventureofDickCole = new String[adventureofDickColeTitle.length];

        boldVentureTitle = jnav.getListOfEps(thrillerCat[5], "Thriller");
        boldVenture = new String[boldVentureTitle.length];

        bostonBlackieTitle = jnav.getListOfEps(thrillerCat[6], "Thriller");
        bostonBlackie = new String[bostonBlackieTitle.length];

        cbsRadioTitle = jnav.getListOfEps(thrillerCat[7], "Thriller");
        cbsRadio = new String[cbsRadioTitle.length];

        dangerousAssignmentTitle = jnav.getListOfEps(thrillerCat[8], "Thriller");
        dangerousAssignment = new String[dangerousAssignmentTitle.length];

        duffysTavernTitle = jnav.getListOfEps(thrillerCat[9], "Thriller");
        duffysTavern = new String[duffysTavernTitle.length];

        mrAndMrsNorthTitle = jnav.getListOfEps(thrillerCat[10], "Thriller");
        mrAndMrsNorth = new String[mrAndMrsNorthTitle.length];

        quietPleaseTitle = jnav.getListOfEps(thrillerCat[11], "Thriller");
        quietPlease = new String[quietPleaseTitle.length];

        suspenseTitle = jnav.getListOfEps(thrillerCat[12], "Thriller");
        suspense = new String[suspenseTitle.length];

        harryLimeTitle = jnav.getListOfEps(thrillerCat[13], "Thriller");
        harryLime = new String[harryLimeTitle.length];

        for (int i = 0; i<nightBeatTitle.length; i++)
        {
            nightBeat[i] = jnav.getFilename(nightBeatTitle[i], thrillerCat[0], "Thriller");
            nbMap.put(nightBeat[i], nightBeatTitle[i]);
        }

        for (int i = 0; i<speedTitle.length; i++)
        {
            speed[i] = jnav.getFilename(speedTitle[i], thrillerCat[1], "Thriller");
            sgMap.put(speed[i], speedTitle[i]);
        }

        for (int i = 0; i<whistlerTitle.length; i++)
        {
            whistler[i] = jnav.getFilename(whistlerTitle[i], thrillerCat[2], "Thriller");
            wsMap.put(whistler[i], whistlerTitle[i]);
        }

        for (int i = 0; i<adventureByMorseTitle.length; i++)
        {
            adventureByMorse[i] = jnav.getFilename(adventureByMorseTitle[i], thrillerCat[3], "Thriller");
            abmMap.put(adventureByMorse[i], adventureByMorseTitle[i]);
        }

        for (int i = 0; i<adventureofDickColeTitle.length; i++)
        {
            adventureofDickCole[i] = jnav.getFilename(adventureofDickColeTitle[i], thrillerCat[4], "Thriller");
            adcMap.put(adventureofDickCole[i], adventureofDickColeTitle[i]);
        }

        for (int i = 0; i<boldVentureTitle.length; i++)
        {
            boldVenture[i] = jnav.getFilename(boldVentureTitle[i], thrillerCat[5], "Thriller");
            bvMap.put(boldVenture[i], boldVentureTitle[i]);
        }

        for (int i = 0; i<bostonBlackieTitle.length; i++)
        {
            bostonBlackie[i] = jnav.getFilename(bostonBlackieTitle[i], thrillerCat[6], "Thriller");
            bbMap.put(bostonBlackie[i], bostonBlackieTitle[i]);
        }

        for (int i = 0; i<cbsRadioTitle.length; i++)
        {
            cbsRadio[i] = jnav.getFilename(cbsRadioTitle[i], thrillerCat[7], "Thriller");
            cbsMap.put(cbsRadio[i], cbsRadioTitle[i]);
        }

        for (int i = 0; i<dangerousAssignmentTitle.length; i++)
        {
            dangerousAssignment[i] = jnav.getFilename(dangerousAssignmentTitle[i], thrillerCat[8], "Thriller");
            daMap.put(dangerousAssignment[i], dangerousAssignmentTitle[i]);
        }

        for (int i = 0; i<duffysTavernTitle.length; i++)
        {
            duffysTavern[i] = jnav.getFilename(duffysTavernTitle[i], thrillerCat[9], "Thriller");
            dtMap.put(duffysTavern[i], duffysTavernTitle[i]);
        }

        for (int i = 0; i<mrAndMrsNorthTitle.length; i++)
        {
            mrAndMrsNorth[i] = jnav.getFilename(mrAndMrsNorthTitle[i], thrillerCat[10], "Thriller");
            mmnMap.put(mrAndMrsNorth[i], mrAndMrsNorthTitle[i]);
        }

        for (int i = 0; i<quietPleaseTitle.length; i++)
        {
            quietPlease[i] = jnav.getFilename(quietPleaseTitle[i], thrillerCat[11], "Thriller");
            qpMap.put(quietPlease[i], quietPleaseTitle[i]);
        }

        for (int i = 0; i<suspenseTitle.length; i++)
        {
            suspense[i] = jnav.getFilename(suspenseTitle[i], thrillerCat[12], "Thriller");
            ssMap.put(suspense[i], suspenseTitle[i]);
        }

        for (int i = 0; i<harryLimeTitle.length; i++)
        {
            harryLime[i] = jnav.getFilename(harryLimeTitle[i], thrillerCat[13], "Thriller");
            hlMap.put(harryLime[i], harryLimeTitle[i]);
        }
        thrillerLoaded = true;
    }

    public void updateSciFiTitles()
    {
        if (scifiLoaded)
        {
            Log.d(TAG, "Science Fiction Already Loaded");
            return;
        }

        // Sci-fi maps
        this.xmMap = new HashMap<>();
        this.isMap = new HashMap<>();
        this.dxMap = new HashMap<>();
        this.fgMap = new HashMap<>();
        this.srMap = new HashMap<>();
        this.ghMap = new HashMap<>();

        // sci-fi
        xminusTitle = jnav.getListOfEps(scifiCat[0], "Science Fiction");
        xminus = new String[xminusTitle.length];

        innerSanctumTitle = jnav.getListOfEps(scifiCat[1], "Science Fiction");
        innerSanctum = new String[innerSanctumTitle.length];

        dimensionXTitle = jnav.getListOfEps(scifiCat[2], "Science Fiction");
        dimensionX = new String[dimensionXTitle.length];

        flashGordonTitle = jnav.getListOfEps(scifiCat[3], "Science Fiction");
        flashGordon = new String[flashGordonTitle.length];

        sciFiRadioTitle = jnav.getListOfEps(scifiCat[4], "Science Fiction");
        sciFiRadio = new String[sciFiRadioTitle.length];

        greenHornetTitle = jnav.getListOfEps(scifiCat[5], "Science Fiction");
        greenHornet = new String[greenHornetTitle.length];

        for (int i = 0; i<xminusTitle.length; i++)
        {
            xminus[i] = jnav.getFilename(xminusTitle[i], scifiCat[0], "Science Fiction");
            xmMap.put(xminus[i], xminusTitle[i]);
        }

        for (int i = 0; i<innerSanctumTitle.length; i++)
        {
            innerSanctum[i] = jnav.getFilename(innerSanctumTitle[i], scifiCat[1], "Science Fiction");
            isMap.put(innerSanctum[i], innerSanctumTitle[i]);
        }

        for (int i = 0; i<dimensionXTitle.length; i++)
        {
            dimensionX[i] = jnav.getFilename(dimensionXTitle[i], scifiCat[2], "Science Fiction");
            dxMap.put(dimensionX[i], dimensionXTitle[i]);
        }

        for (int i = 0; i<flashGordonTitle.length; i++)
        {
            flashGordon[i] = jnav.getFilename(flashGordonTitle[i], scifiCat[3], "Science Fiction");
            fgMap.put(flashGordon[i], flashGordonTitle[i]);
        }

        for (int i = 0; i<sciFiRadioTitle.length; i++)
        {
            sciFiRadio[i] = jnav.getFilename(sciFiRadioTitle[i], scifiCat[4], "Science Fiction");
            srMap.put(sciFiRadio[i], sciFiRadioTitle[i]);
        }

        for (int i = 0; i<greenHornetTitle.length; i++)
        {
            greenHornet[i] = jnav.getFilename(greenHornetTitle[i], scifiCat[5], "Science Fiction");
            ghMap.put(greenHornet[i], greenHornetTitle[i]);
        }
        scifiLoaded = true;
    }

    public void updateWesternTitles()
    {

        if (westernLoaded)
        {
            Log.d(TAG, "Western Already Loaded");
            return;
        }

        // Western Maps
        this.hcMap = new HashMap<>();
        this.flMap = new HashMap<>();
        this.lrMap = new HashMap<>();
        this.poMap = new HashMap<>();
        this.hgMap = new HashMap<>();

        // Western
        loneRangerTitle = jnav.getListOfEps(westernCat[0], "Western");
        loneRanger = new String[loneRangerTitle.length];

        hopalongTitle = jnav.getListOfEps(westernCat[1], "Western");
        hopalong = new String[hopalongTitle.length];

        PatOTitle = jnav.getListOfEps(westernCat[2], "Western");
        PatO = new String[PatOTitle.length];

        ftLaramieTitle = jnav.getListOfEps(westernCat[3], "Western");
        fortLaramie = new String[ftLaramieTitle.length];

        HaveGunTitle = jnav.getListOfEps(westernCat[4], "Western");
        HaveGun = new String[HaveGunTitle.length];

        for (int i = 0; i<loneRangerTitle.length; i++)
        {
            loneRanger[i] = jnav.getFilename(loneRangerTitle[i], westernCat[0], "Western");
            lrMap.put(loneRanger[i], loneRangerTitle[i]);
        }

        for (int i = 0; i<hopalongTitle.length; i++)
        {
            hopalong[i] = jnav.getFilename(hopalongTitle[i], westernCat[1], "Western");
            hcMap.put(hopalong[i], hopalongTitle[i]);
        }

        for (int i = 0; i<PatOTitle.length; i++)
        {
            PatO[i] = jnav.getFilename(PatOTitle[i], westernCat[2], "Western");
            poMap.put(PatO[i], PatOTitle[i]);
        }

        for (int i = 0; i<ftLaramieTitle.length; i++)
        {
            fortLaramie[i] = jnav.getFilename(ftLaramieTitle[i], westernCat[3], "Western");
            flMap.put(fortLaramie[i], ftLaramieTitle[i]);
        }

        for (int i = 0; i<HaveGunTitle.length; i++)
        {
            HaveGun[i] = jnav.getFilename(HaveGunTitle[i], westernCat[4], "Western");
            hgMap.put(HaveGun[i], HaveGunTitle[i]);
        }

        westernLoaded = true;
    }

    public final String[] comedyCat = {
            "Burns And Allen",
            "Fibber McGee And Molly",
            "The Great GilderSleeves",
            "Martin And Lewis",
            "Jack Benny",
            "Bob Hope",
            "Our Miss Brooks",
            "Father Knows Best",
            "Ozzie And Harriet", // new
            "The Life Of Riley",  // new
            "Blondie"             // new
    };

    public final String[] scifiCat = {
            "XMinus1",
            "Inner Sanctum",
            "Dimension X",
            "Flash Gordon", // new
            "SciFi Radio",       // new
            "The Green Hornet"   // new
    };

    public final String[] thrillerCat = {
            "Night Beat",
            "Speed Gibson",
            "The Whistler",
            "Adventures By Morse",      // new
            "Adventures of Dick Cole",  // new
            "Bold Venture",             // new
            "Boston Blackie",           // new
            "CBS Radio Mystery Theater",  // new
            "Dangerous Assignment",       // new
            "Duffys Tavern",              // new
            "Mr and Mrs North",           // new
            "Quiet Please",               // new
            "Suspense",                   // new
            "The Lives Of Harry Lime"     // new
    };

    public final String[] westernCat = {
            "Lone Ranger",
            "Hopalong Cassidy",
            "Pat O",
            "Fort Laramie",
            "Have Gun Will Travel"   // new
    };

    public final String[] category = {
            "Comedy",
            "Science Fiction",
            "Thriller",
            "Western"
    };

    // comedy
    public String[] getBurnsAllen() { return burnsAllen; }
    public String[] getFibber() { return fibber; }
    public String[] getGilder() { return gilderSleeves; }
    public String[] getMartin() { return martinAndLewis; }
    public String[] getJackBenny() { return jackBenny; }
    public String[] getBobHope() { return bobHope; }
    public String[] getMissBrooks() { return missBrooks; }
    public String[] getfk() { return fatherKnowsBest; }
    public String[] getoh() { return ozzieAndHarriet; }
    public String[] getor() { return lifeOfRiley; }


    public HashMap<String, String> getBaMap() {
        if (!baMap.containsKey("http://www.ruffinapps.com/Audio/BurnsAllan/470227_Gracie_Allen_Inc.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return baMap;
    }
    public HashMap<String, String> getFbMap() {
        if (!fbMap.containsKey("http://www.ruffinapps.com/Audio/FibberMcGeeandMolly/400130_Fibbers_Old_Suit.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return fbMap; }
    public HashMap<String, String> getMlMap() {
        if (!mlMap.containsKey("http://www.johnnieruffin.com/Audio/MartinAndLewis_OldTimeRadio/MartinLewisShow490612_011_BurlIves.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return mlMap;}
    public HashMap<String, String> getGlMap() {
        if (!glMap.containsKey("http://www.ruffinapps.com/Audio/GilderSleeves/The_Great_Gildersleeve_41-09-21_004_Marjories_Girl_Friend_Visits.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            // Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return glMap;}
    public HashMap<String, String> getJbMap() {
        if (!jbMap.containsKey("http://www.johnnieruffin.com/Audio/JackBenny/Jb1933-04-21EdwardGRobinson.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return jbMap;}
    public HashMap<String, String> getBhMap() {
        if (!bhMap.containsKey("http://www.JohnnieRuffin.com/Audio/BobHope/381004_-_002_-_Olivia_DeHavilland.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return bhMap;}
    public HashMap<String, String> getMbMap() {
        if (!mbMap.containsKey("http://www.ruffinapps.com/Audio/Brooks/Our_Miss_Brooks_490501_039_Walter_Vs_Stretch_Grudge_Match.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return mbMap; }
    public HashMap<String, String> getFkMap() {
        if (!fkMap.containsKey("http://www.ruffinapps.com/Audio/FatherKnowsBest/Fkb1950-10-12051TheSkunkMustGo.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return fkMap; }
    public HashMap<String, String> getOhMap() {
        if (!ohMap.containsKey("http://www.ruffinapps.com/Audio/OzzieHarriet/Oh1945-05-20033afrsTheNewArrival.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            // Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return ohMap; }
    public HashMap<String, String> getOrMap() {
        if (!orMap.containsKey("http://www.ruffinapps.com/Audio/TheLifeOfRiley/Lor1944-02-06004RileyRentsAHse-movesFromHotel.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return orMap;
    }

    public HashMap<String, String> getBlMap() {
        if (!blMap.containsKey("http://www.ruffinapps.com/Audio/Blondie/Bd1940-04-01040AprilFoolsDay.mp3"))
        {
            comedyLoaded = false;
            updateComedyTitles();
            Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return blMap;
    }

    // Sci-Fi
    public String[] getXM() { return xminus; }
    public String[] getIs() { return innerSanctum; }
    public String[] getDx() { return dimensionX; }
    public String[] getFg() { return flashGordon; }
    public String[] getSr() { return sciFiRadio; }
    public String[] getGh() { return greenHornet; }

    public HashMap<String, String> getXMMap() {
        if (!xmMap.containsKey("http://www.johnnieruffin.com/Audio/XMinus1/xminusone_570905_SaucerOfLoneliness_remake.mp3"))
        {
            scifiLoaded = false;
            updateSciFiTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return xmMap; }
    public HashMap<String, String> getIsMap() {
        if (!isMap.containsKey("http://www.johnnieruffin.com/Audio/InnerSanctum/Inner_Sanctum_451211_The_Dark_Chamber.mp3"))
        {
            scifiLoaded = false;
            updateSciFiTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return isMap;}
    public HashMap<String, String> getDxMap() {
        if (!dxMap.containsKey("http://www.JohnnieRuffin.com/Audio/DimensionX/Dimx_e041_Courtesy.mp3"))
        {
            scifiLoaded = false;
            updateSciFiTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return dxMap; }
    public HashMap<String, String> getFgMap() {
        if (!fgMap.containsKey("http://www.ruffinapps.com/Audio/FlashGordon1935/Flash_Gordon_35-10-19_25_Landing_in_Malaysia.mp3"))
        {
            scifiLoaded = false;
            updateSciFiTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return fgMap; }
    public HashMap<String, String> getSrMap() {
        if (!srMap.containsKey("http://www.ruffinapps.com/Audio/scifiRadio/Sci-fiRadio19-FieldOfVisionByUrsulaK.Leguinplay.mp3"))
        {
            scifiLoaded = false;
            updateSciFiTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return srMap; }
    public HashMap<String, String> getGhMap() {
        if (!ghMap.containsKey("http://www.ruffinapps.com/Audio/TheGreenHornet/Thegreenhornet-480120AMatterOfEvidence.mp3"))
        {
            scifiLoaded = false;
            updateSciFiTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return ghMap;
    }

    // Thriller
    public String[] getnb() { return nightBeat; }
    public String[] getsg() { return speed; }
    public String [] getws() { return whistler; }
    public String [] getabm() { return adventureByMorse; }
    public String [] getadc() { return adventureofDickCole; }
    public String [] getbl() { return blondie; }
    public String [] getbv() { return boldVenture; }
    public String [] getbb() { return bostonBlackie; }
    public String [] getcbs() { return cbsRadio; }
    public String [] getda() { return dangerousAssignment; }
    public String [] getdt() { return duffysTavern; }
    public String [] getmmn() { return mrAndMrsNorth; }
    public String [] getqp() { return quietPlease; }
    public String [] getss() { return  suspense; }
    public String [] gethl() { return harryLime; }

    public HashMap<String, String> getNbMap() {
        if (!nbMap.containsKey("http://www.johnnieruffin.com/Audio/NightBeat/Night_Beat_52_08_14_0106_His_Name_Was_Luke.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return nbMap; }
    public HashMap<String, String> getSgMap() {
        if (!sgMap.containsKey("http://www.johnnieruffin.com/Audio/Speed/SGISP_1940-05-25.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return sgMap; }
    public HashMap<String, String> getWsMap() {
        if (!wsMap.containsKey("http://www.johnnieruffin.com/Audio/TheWhistler/Whistler_421129_Avarice.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return wsMap; }
    public HashMap<String, String> getAbmMap() {
        if (!abmMap.containsKey("http://www.ruffinapps.com/Audio/AdventuresByMorse/Abym1944-12-30052ItsDismalToDiePt03BadMedicineForTheDoctor.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return abmMap; }
    public HashMap<String, String> getAdcMap() {
        if (!adcMap.containsKey("http://www.ruffinapps.com/Audio/AdventuresofDickCole/AoDC_xx-xx-xx-Rangefinder_Spy_Case.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return adcMap;
    }

    public HashMap<String, String> getBvMap() {
        if (!bvMap.containsKey("http://www.ruffinapps.com/Audio/BoldVenture/BoldVenture52033154TheRunawayWife.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return bvMap;
    }

    public HashMap<String, String> getBbMap() {
        if (!bbMap.containsKey("http://www.ruffinapps.com/Audio/BostonBlackie/BBLK.1947.04.01_103_THE_BUS_TO_VALLEY_JUNCTION.MP3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return bbMap; }
    public HashMap<String, String> getCbsMap() {
        if (!cbsMap.containsKey("http://www.ruffinapps.com/Audio/cbs_radio_mystery_theater/cbsrmt_1395_the_smile.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return cbsMap; }
    public HashMap<String, String> getDaMap() {
        if (!daMap.containsKey("http://www.ruffinapps.com/Audio/DangerousAssignment/Dangerous_assignment-53-05-13_158_getTheManTryingToSabotageCooperationTalks.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return daMap; }
    public HashMap<String, String> getDtMap() {
        if (!dtMap.containsKey("http://www.ruffinapps.com/Audio/DuffysTavern/duff.1951.11.02_Spanish_Floor_Show.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return dtMap; }
    public HashMap<String, String> getMmnMap() {
        if (!mmnMap.containsKey("http://www.ruffinapps.com/Audio/MrandMrsNorth/MMN_D_B_Die_Hard_520325_is_INVALID_DATE_.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return mmnMap; }
    public HashMap<String, String> getQpMap() {
        if (!qpMap.containsKey("http://www.ruffinapps.com/Audio/QuietPlease/Quiet_Please_490611_104_The_Hat_the_Bed_and_John_J_Catherine.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return qpMap; }
    public HashMap<String, String> getSsMap() {
        if (!ssMap.containsKey("http://www.ruffinapps.com/Audio/suspense/54-10-28TheShelter.MP3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return ssMap; }
    public HashMap<String, String> getHlMap() {
        if (!hlMap.containsKey("http://www.ruffinapps.com/Audio/TheLivesOfHarryLime/Harry_Lime_52-07-25_52_Greek_Meets_Greek.mp3"))
        {
            thrillerLoaded = false;
            updateThrillerTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return hlMap; }

    // Western
   public String[] gethc() { return hopalong; }
    public String[] getfl() { return fortLaramie; }
    public String[] getpo() { return PatO; }
    public String[] getlr() { return loneRanger; }
    public String[] gethg() { return HaveGun; }

    public HashMap<String, String> getHcMap() {
        if (!hcMap.containsKey("http://www.johnnieruffin.com/Audio/Hopalong/Hopalong_Cassidy_520223_0101_Right_Rope_Wrong_Neck.mp3"))
        {
            westernLoaded = false;
            updateWesternTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return hcMap; }
    public HashMap<String, String> getFlMap() {
        if (!flMap.containsKey("http://www.johnnieruffin.com/Audio/FtLaramie/FrtL1956-10-21039IndianScout1.mp3"))
        {
            westernLoaded = false;
            updateWesternTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return flMap; }
    public HashMap<String, String> getPoMap() {
        if (!poMap.containsKey("http://www.ruffinapps.com/Audio/PatO/Pat_ODaniel_and_His_Hillbilly_Boys_42.mp3"))
        {
            westernLoaded = false;
            updateWesternTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return poMap; }
    public HashMap<String, String> getLrMap() {
        if (!lrMap.containsKey("http://www.ruffinapps.com/Audio/LoneRanger/United We Stand.mp3"))
        {
            westernLoaded = false;
            updateWesternTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return lrMap; }
    public HashMap<String, String> getHgMap() {
        if (!hgMap.containsKey("http://www.ruffinapps.com/Audio/HaveGunWillTravel/HaveGunWillTravel601106103TheOdds_64kb.mp3"))
        {
            westernLoaded = false;
            updateWesternTitles();
            //Log.d(TAG, "-------------Does not Exist!!!----------------");
        }
        return hgMap; }
}
