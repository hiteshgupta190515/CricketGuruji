package com.inclass.cricketguruji.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;
import com.inclass.cricketguruji.AppController;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.PlayerConstants;
import com.inclass.cricketguruji.model.StageModel;
import com.inclass.cricketguruji.ui.DomesticTeams;
import com.inclass.cricketguruji.ui.Fragments.PlayerBattingCareer;
import com.inclass.cricketguruji.ui.Fragments.PlayerBowlingCareer;
import com.inclass.cricketguruji.ui.Fragments.PlayerInfo;
import com.inclass.cricketguruji.ui.NationalTeams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerDetailActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    Toolbar toolbar;
    String playerName,playerId;
    public PlayerConstants playerConstants;
    String[] battingHeaderArray = {"Matches","Innings","Runs","Not Out", "Balls","Highest","SR"
    ,"Average","Four's","Six's","100s","50s"};

    String[] bowlHeaderArray = {"Matches","Innings","Overs","Maidens","Runs","Wickets", "Wide","No Balls","SR"
            ,"Average","Eco","4w","5w","10w"};

    public ArrayList<PlayerConstants> playerInfoArrayList = new ArrayList<PlayerConstants>();
    public ArrayList<PlayerConstants> playerBlowingArrayList = new ArrayList<PlayerConstants>();

    public ArrayList<String> playerTestBatArrayList = new ArrayList<String>();
    public ArrayList<String> playerOdiBatArrayList = new ArrayList<String>();
    public ArrayList<String> playerT20BatArrayList = new ArrayList<String>();
    public ArrayList<String> playerIplBatArrayList = new ArrayList<String>();

    public ArrayList<String> testhighbatting = new ArrayList<String>();
    public ArrayList<String> odihighbatting = new ArrayList<String>();
    public ArrayList<String> t20highbatting = new ArrayList<String>();
    public ArrayList<String> iplhighbatting = new ArrayList<String>();

    // Test variables
    int totaltestmatches = 0,totaltestinnnings = 0,totaltestruns = 0,totaltestnotout = 0, totaltestballs = 0,
            totaltestfour = 0,totaltestsix = 0, totaltesthundred = 0,totaltestfifty = 0;
    double  avgtestsr = 0,testavg = 0;
    String avgtesthighscore = "0";
    // ODI variables
    int totalodimatches = 0,totalodiinnnings = 0,totalodiruns = 0,totalodinotout = 0, totalodiballs = 0,
            totalodifour = 0,totalodisix = 0, totalodihundred = 0,totalodififty = 0;
    double  avgodisr = 0,odiavg = 0;
    String avgodihighscore = "0";
    // T20 variables
    int totalt20matches = 0,totalt20innnings = 0,totalt20runs = 0,totalt20notout = 0, totalt20balls = 0,
            totalt20four = 0,totalt20six = 0, totalt20hundred = 0,totalt20fifty = 0;
    double avgt20sr = 0,t20avg = 0;
    String avgt20highscore = "0";
    // IPL variables
    int totaliplmatches = 0,totaliplinnnings = 0,totaliplruns = 0,totaliplnotout = 0, totaliplballs = 0,
            totaliplfour = 0,totaliplsix = 0, totaliplhundred = 0,totaliplfifty = 0;
    double avgiplsr = 0,iplavg = 0;
    String avgiplhighscore = "0";

    double test_avg = 0;
    double test_sr = 0;
    double odi_avg = 0;
    double odi_sr = 0;
    double t20_avg = 0;
    double t20_sr = 0;
    double ipl_avg = 0;
    double ipl_sr = 0;

    public ArrayList<String> playerTestBowlArrayList = new ArrayList<String>();
    public ArrayList<String> playerOdiBowlArrayList = new ArrayList<String>();
    public ArrayList<String> playerT20BowlArrayList = new ArrayList<String>();
    public ArrayList<String> playerIplBowlArrayList = new ArrayList<String>();
    //Bowling Variables Starts

    // Test BOWL variables
    int bowltestmatches = 0,bowltestinnnings = 0,bowltestmadiens = 0, bowltestruns = 0,
            bowltestwickets = 0,bowltestwide = 0, bowltestnoballs = 0,bowltestfour = 0,bowltestfive = 0, bowltestten = 0;
    double  bowltestavg = 0,bowltesteco = 0,bowltestsr = 0,bowltestovers = 0;

    // ODI BOWL variables
    int bowlodimatches = 0,bowlodiinnnings = 0,bowlodimadiens = 0, bowlodiruns = 0,
            bowlodiwickets = 0,bowlodiwide = 0, bowlodinoballs = 0,bowlodifour = 0,bowlodifive = 0, bowloditen = 0;
    double  bowlodiavg = 0,bowlodieco = 0,bowlodisr = 0,bowlodiovers = 0;

    // T20  BOWL variables
    int bowlt20matches = 0,bowlt20innnings = 0,bowlt20madiens = 0, bowlt20runs = 0,
            bowlt20wickets = 0,bowlt20wide = 0, bowlt20noballs = 0,bowlt20four = 0,bowlt20five = 0, bowlt20ten = 0;
    double  bowlt20avg = 0,bowlt20eco = 0,bowlt20sr = 0,bowlt20overs = 0;

    // IPL BOWL variables
    int bowliplmatches = 0,bowliplinnnings = 0,bowliplmadiens = 0, bowliplruns = 0,
            bowliplwickets = 0,bowliplwide = 0, bowliplnoballs = 0,bowliplfour = 0,bowliplfive = 0, bowliplten = 0;
    double  bowliplavg = 0,bowlipleco = 0,bowliplsr = 0,bowliplovers = 0;

    double bowltest_avg = 0;
    double bowltest_eco = 0;
    double bowltest_sr = 0;
    double bowlodi_avg = 0;
    double bowlodi_eco = 0;
    double bowlodi_sr = 0;
    double bowlt20_avg = 0;
    double bowlt20_eco = 0;
    double bowlt20_sr = 0;
    double bowlipl_avg = 0;
    double bowlipl_eco = 0;
    double bowlipl_sr = 0;

    // Bowling Variables Ends


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                playerId= null;
                playerName= null;
            } else {
                playerId= extras.getString("playerid");
                playerName= extras.getString("playername");
            }
        } else {
            playerId= (String) savedInstanceState.getSerializable("playerid");
            playerName= (String) savedInstanceState.getSerializable("playername");
        }
        initComponent();
    }

    private void initComponent() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(playerName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        makeJsonArrayRequest();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeJsonArrayRequest() {
        String tag_string_req1 = "string_req";
        String urlJsonArry = "https://cricket.sportmonks.com/api/v2.0/players/"+playerId+
                "?api_token=bq9QWLmC7ykuVHn1GWdogPmaWy4Swn3Q7n2V3PBpTlang6CQf4RqRwcLGBvG&include=career";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest strReq1 = new StringRequest(Request.Method.GET,
                urlJsonArry, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("PlayerInfo", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                        playerConstants = new PlayerConstants();
                        playerConstants.fullname = jsonObject.getJSONObject("data").getString("fullname");
                        playerConstants.image_path = jsonObject.getJSONObject("data").getString("image_path");
                        playerConstants.dateofbirth = jsonObject.getJSONObject("data").getString("dateofbirth");
                        playerConstants.gender = jsonObject.getJSONObject("data").getString("gender");
                        playerConstants.battingstyle = jsonObject.getJSONObject("data").getString("battingstyle");
                        playerConstants.bowlingstyle = jsonObject.getJSONObject("data").getString("bowlingstyle");
                        playerConstants.positionname = jsonObject.getJSONObject("data").getJSONObject("position").getString("name");


                        int avg_counter = 0;
                        JSONArray players = jsonObject.getJSONObject("data").getJSONArray("career");
                        for (int i = 0; i < players.length(); i++)
                        {
                            JSONObject jsonObject1 = players.getJSONObject(i);
                            if(jsonObject1.getString("type").equals("Test/5day")) {
                                if(jsonObject1.isNull("batting")){

                                }else {
                                    //Batting Json
                                    totaltestmatches += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("matches"));
                                    totaltestinnnings += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("innings"));
                                    totaltestruns += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("runs_scored"));
                                    totaltestnotout += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("not_outs"));
                                    totaltestballs += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("balls_faced"));
                                    totaltestfour += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("four_x"));
                                    totaltestsix += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("six_x"));
                                    totaltesthundred += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("hundreds"));
                                    totaltestfifty += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("fifties"));
                                    avgtestsr += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("strike_rate"));
                                    testavg += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("average"));
                                    testhighbatting.add(jsonObject1.getJSONObject("batting").getString("highest_inning_score"));
                                    avgtesthighscore = Collections.max(testhighbatting);
                                }
                                // Bowl Json
                                if(jsonObject1.isNull("bowling")){

                                }else {
                                    bowltestmatches += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("matches"));
                                    bowltestinnnings += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("innings"));
                                    bowltestovers += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("overs"));
                                    bowltestmadiens += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("medians"));
                                    bowltestruns += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("runs"));
                                    bowltestwickets += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wickets"));
                                    bowltestwide += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wide"));
                                    bowltestnoballs += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("noball"));
                                    bowltestfour += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("four_wickets"));
                                    bowltestfive += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("five_wickets"));
                                    bowltestten += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("ten_wickets"));
                                    bowltestavg += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("average"));
                                    bowltesteco += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("econ_rate"));
                                    String bowlsr = jsonObject1.getJSONObject("bowling").getString("strike_rate");
                                    if (bowlsr.equals("null"))
                                        bowlsr = "0";
                                    bowltestsr += Double.parseDouble(bowlsr);
                                }
                                avg_counter++;
                            } else if(jsonObject1.getString("type").equals("ODI")) {
                                if(jsonObject1.isNull("batting")){


                                }else {
                                    totalodimatches += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("matches"));
                                    totalodiinnnings += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("innings"));
                                    totalodiruns += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("runs_scored"));
                                    totalodinotout += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("not_outs"));
                                    totalodiballs += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("balls_faced"));
                                    totalodifour += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("four_x"));
                                    totalodisix += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("six_x"));
                                    totalodihundred += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("hundreds"));
                                    totalodififty += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("fifties"));
                                    String strike_rate = jsonObject1.getJSONObject("batting").getString("strike_rate");
                                    if (strike_rate.equals("null"))
                                        strike_rate = "0";
                                    avgodisr += Double.parseDouble(strike_rate);
                                    odiavg += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("average"));
                                    odihighbatting.add(jsonObject1.getJSONObject("batting").getString("highest_inning_score"));
                                    avgodihighscore = Collections.max(odihighbatting);
                                }
                                // Bowl Json
                                if(jsonObject1.isNull("bowling")){

                                }else {
                                    bowlodimatches += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("matches"));
                                    bowlodiinnnings += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("innings"));
                                    bowlodiovers += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("overs"));
                                    bowlodimadiens += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("medians"));
                                    bowlodiruns += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("runs"));
                                    bowlodiwickets += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wickets"));
                                    bowlodiwide += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wide"));
                                    bowlodinoballs += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("noball"));
                                    bowlodifour += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("four_wickets"));
                                    bowlodifive += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("five_wickets"));
                                    bowloditen += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("ten_wickets"));
                                    bowlodiavg += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("average"));
                                    bowlodieco += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("econ_rate"));
                                    String bowlsr = jsonObject1.getJSONObject("bowling").getString("strike_rate");
                                    if (bowlsr.equals("null"))
                                        bowlsr = "0";
                                    bowlodisr += Double.parseDouble(bowlsr);
                                }
                                avg_counter++;
                            } else if(jsonObject1.getString("type").equals("T20I") || jsonObject1.getString("type").equals("T20") ) {
                                if(jsonObject1.isNull("batting")){

                                }else {
                                    totalt20matches += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("matches"));
                                    totalt20innnings += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("innings"));
                                    totalt20runs += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("runs_scored"));
                                    totalt20notout += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("not_outs"));
                                    totalt20balls += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("balls_faced"));
                                    totalt20four += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("four_x"));
                                    totalt20six += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("six_x"));
                                    totalt20hundred += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("hundreds"));
                                    totalt20fifty += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("fifties"));
                                    avgt20sr += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("strike_rate"));
                                    t20avg += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("average"));
                                    t20highbatting.add(jsonObject1.getJSONObject("batting").getString("highest_inning_score"));
                                    avgt20highscore = Collections.max(t20highbatting);
                                }
                                // Bowl Json
                                if(jsonObject1.isNull("bowling")){

                                }else {
                                    bowlt20matches += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("matches"));
                                    bowlt20innnings += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("innings"));
                                    bowlt20overs += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("overs"));
                                    bowlt20madiens += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("medians"));
                                    bowlt20runs += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("runs"));
                                    bowlt20wickets += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wickets"));
                                    bowlt20wide += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wide"));
                                    bowlt20noballs += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("noball"));
                                    bowlt20four += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("four_wickets"));
                                    bowlt20five += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("five_wickets"));
                                    bowlt20ten += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("ten_wickets"));
                                    bowlt20avg += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("average"));
                                    bowlt20eco += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("econ_rate"));
                                    String bowlsr = jsonObject1.getJSONObject("bowling").getString("strike_rate");
                                    if (bowlsr.equals("null"))
                                        bowlsr = "0";
                                    bowlt20sr += Double.parseDouble(bowlsr);
                                }
                                avg_counter++;
                            } else if(jsonObject1.getString("type").equals("IPL") ) {
                                if(jsonObject1.isNull("batting")){

                                }else {
                                    totaliplmatches += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("matches"));
                                    totaliplinnnings += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("innings"));
                                    totaliplruns += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("runs_scored"));
                                    totaliplnotout += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("not_outs"));
                                    totaliplballs += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("balls_faced"));
                                    totaliplfour += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("four_x"));
                                    totaliplsix += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("six_x"));
                                    totaliplhundred += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("hundreds"));
                                    totaliplfifty += Integer.parseInt(jsonObject1.getJSONObject("batting").getString("fifties"));
                                    avgiplsr += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("strike_rate"));
                                    iplavg += Double.parseDouble(jsonObject1.getJSONObject("batting").getString("average"));
                                    iplhighbatting.add(jsonObject1.getJSONObject("batting").getString("highest_inning_score"));
                                    avgiplhighscore = Collections.max(iplhighbatting);
                                }
                                // Bowl Json
                                if(jsonObject1.isNull("bowling")){

                                }else {
                                    bowliplmatches += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("matches"));
                                    bowliplinnnings += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("innings"));
                                    bowliplovers += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("overs"));
                                    bowliplmadiens += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("medians"));
                                    bowliplruns += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("runs"));
                                    bowliplwickets += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wickets"));
                                    bowliplwide += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("wide"));
                                    bowliplnoballs += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("noball"));
                                    bowliplfour += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("four_wickets"));
                                    bowliplfive += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("five_wickets"));
                                    bowliplten += Integer.parseInt(jsonObject1.getJSONObject("bowling").getString("ten_wickets"));
                                    bowliplavg += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("average"));
                                    bowlipleco += Double.parseDouble(jsonObject1.getJSONObject("bowling").getString("econ_rate"));
                                    String bowlsr = jsonObject1.getJSONObject("bowling").getString("strike_rate");
                                    if (bowlsr.equals("null"))
                                        bowlsr = "0";
                                    bowliplsr += Double.parseDouble(bowlsr);
                                }
                                avg_counter++;
                            }

                        }
                        test_avg = testavg/avg_counter;
                        test_sr = avgtestsr/avg_counter;
                        odi_avg = avgodisr/avg_counter;
                        odi_sr = odiavg/avg_counter;
                        t20_avg = avgt20sr/avg_counter;
                        t20_sr = t20avg/avg_counter;
                        ipl_avg = avgiplsr/avg_counter;
                        ipl_sr = iplavg/avg_counter;

                        // Bowling
                        bowltest_avg = bowltestavg/avg_counter;;
                        bowltest_eco = bowltesteco/avg_counter;;
                        bowltest_sr = bowltestsr/avg_counter;;
                        bowlodi_avg = bowlodiavg/avg_counter;;
                        bowlodi_eco = bowlodieco/avg_counter;;
                        bowlodi_sr = bowlodisr/avg_counter;;
                        bowlt20_avg = bowlt20avg/avg_counter;;
                        bowlt20_eco = bowlt20eco/avg_counter;;
                        bowlt20_sr = bowlt20sr/avg_counter;;
                        bowlipl_avg = bowliplavg/avg_counter;;
                        bowlipl_eco = bowlipleco/avg_counter;;
                        bowlipl_sr = bowliplsr/avg_counter;;


                    playerInfoArrayList.add(playerConstants);

                    playerTestBatArrayList.add(String.valueOf(totaltestmatches));
                    playerTestBatArrayList.add(String.valueOf(totaltestinnnings));
                    playerTestBatArrayList.add(String.valueOf(totaltestruns));
                    playerTestBatArrayList.add(String.valueOf(totaltestnotout));
                    playerTestBatArrayList.add(String.valueOf(totaltestballs));
                    playerTestBatArrayList.add(String.valueOf(avgtesthighscore));
                    playerTestBatArrayList.add(String.format("%.2f",test_sr));
                    playerTestBatArrayList.add(String.format("%.2f",test_avg));
                    playerTestBatArrayList.add(String.valueOf(totaltestfour));
                    playerTestBatArrayList.add(String.valueOf(totaltestsix));
                    playerTestBatArrayList.add(String.valueOf(totaltesthundred));
                    playerTestBatArrayList.add(String.valueOf(totaltestfifty));



                    playerOdiBatArrayList.add(String.valueOf(totalodimatches));
                    playerOdiBatArrayList.add(String.valueOf(totalodiinnnings));
                    playerOdiBatArrayList.add(String.valueOf(totalodiruns));
                    playerOdiBatArrayList.add(String.valueOf(totalodinotout));
                    playerOdiBatArrayList.add(String.valueOf(totalodiballs));
                    playerOdiBatArrayList.add(String.valueOf(avgodihighscore));
                    playerOdiBatArrayList.add(String.format("%.2f",odi_sr));
                    playerOdiBatArrayList.add(String.format("%.2f",odi_avg));
                    playerOdiBatArrayList.add(String.valueOf(totalodifour));
                    playerOdiBatArrayList.add(String.valueOf(totalodisix));
                    playerOdiBatArrayList.add(String.valueOf(totalodihundred));
                    playerOdiBatArrayList.add(String.valueOf(totalodififty));

                    playerT20BatArrayList.add(String.valueOf(totalt20matches));
                    playerT20BatArrayList.add(String.valueOf(totalt20innnings));
                    playerT20BatArrayList.add(String.valueOf(totalt20runs));
                    playerT20BatArrayList.add(String.valueOf(totalt20notout));
                    playerT20BatArrayList.add(String.valueOf(totalt20balls));
                    playerT20BatArrayList.add(String.valueOf(avgt20highscore));
                    playerT20BatArrayList.add(String.format("%.2f",t20_sr));
                    playerT20BatArrayList.add(String.format("%.2f",t20_avg));
                    playerT20BatArrayList.add(String.valueOf(totalt20four));
                    playerT20BatArrayList.add(String.valueOf(totalt20six));
                    playerT20BatArrayList.add(String.valueOf(totalt20hundred));
                    playerT20BatArrayList.add(String.valueOf(totalt20fifty));

                    playerIplBatArrayList.add(String.valueOf(totaliplmatches));
                    playerIplBatArrayList.add(String.valueOf(totaliplinnnings));
                    playerIplBatArrayList.add(String.valueOf(totaliplruns));
                    playerIplBatArrayList.add(String.valueOf(totaliplnotout));
                    playerIplBatArrayList.add(String.valueOf(totaliplballs));
                    playerIplBatArrayList.add(String.valueOf(avgiplhighscore));
                    playerIplBatArrayList.add(String.format("%.2f",ipl_sr));
                    playerIplBatArrayList.add(String.format("%.2f",ipl_avg));
                    playerIplBatArrayList.add(String.valueOf(totaliplfour));
                    playerIplBatArrayList.add(String.valueOf(totaliplsix));
                    playerIplBatArrayList.add(String.valueOf(totaliplhundred));
                    playerIplBatArrayList.add(String.valueOf(totaliplfifty));

                    // Bowling Arrays Starts

                    playerTestBowlArrayList.add(String.valueOf(bowltestmatches));
                    playerTestBowlArrayList.add(String.valueOf(bowltestinnnings));
                    playerTestBowlArrayList.add(String.format("%.2f",bowltestovers));
                    playerTestBowlArrayList.add(String.valueOf(bowltestmadiens));
                    playerTestBowlArrayList.add(String.valueOf(totaltestruns));
                    playerTestBowlArrayList.add(String.valueOf(bowltestwickets));
                    playerTestBowlArrayList.add(String.valueOf(bowltestwide));
                    playerTestBowlArrayList.add(String.valueOf(bowltestnoballs));
                    playerTestBowlArrayList.add(String.format("%.2f",bowltest_sr));
                    playerTestBowlArrayList.add(String.format("%.2f",bowltest_avg));
                    playerTestBowlArrayList.add(String.format("%.2f",bowltest_eco));
                    playerTestBowlArrayList.add(String.valueOf(bowltestfour));
                    playerTestBowlArrayList.add(String.valueOf(bowltestfive));
                    playerTestBowlArrayList.add(String.valueOf(bowltestten));

                    playerOdiBowlArrayList.add(String.valueOf(bowlodimatches));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodiinnnings));
                    playerOdiBowlArrayList.add(String.format("%.2f",bowlodiovers));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodimadiens));
                    playerOdiBowlArrayList.add(String.valueOf(totalodiruns));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodiwickets));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodiwide));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodinoballs));
                    playerOdiBowlArrayList.add(String.format("%.2f",bowlodi_sr));
                    playerOdiBowlArrayList.add(String.format("%.2f",bowlodi_avg));
                    playerOdiBowlArrayList.add(String.format("%.2f",bowlodi_eco));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodifour));
                    playerOdiBowlArrayList.add(String.valueOf(bowlodifive));
                    playerOdiBowlArrayList.add(String.valueOf(bowloditen));

                    playerT20BowlArrayList.add(String.valueOf(bowlt20matches));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20innnings));
                    playerT20BowlArrayList.add(String.format("%.2f",bowlt20overs));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20madiens));
                    playerT20BowlArrayList.add(String.valueOf(totalt20runs));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20wickets));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20wide));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20noballs));
                    playerT20BowlArrayList.add(String.format("%.2f",bowlt20_sr));
                    playerT20BowlArrayList.add(String.format("%.2f",bowlt20_avg));
                    playerT20BowlArrayList.add(String.format("%.2f",bowlt20_eco));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20four));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20five));
                    playerT20BowlArrayList.add(String.valueOf(bowlt20ten));

                    playerIplBowlArrayList.add(String.valueOf(bowliplmatches));
                    playerIplBowlArrayList.add(String.valueOf(bowliplinnnings));
                    playerIplBowlArrayList.add(String.format("%.2f",bowliplovers));
                    playerIplBowlArrayList.add(String.valueOf(bowliplmadiens));
                    playerIplBowlArrayList.add(String.valueOf(totaliplruns));
                    playerIplBowlArrayList.add(String.valueOf(bowliplwickets));
                    playerIplBowlArrayList.add(String.valueOf(bowliplwide));
                    playerIplBowlArrayList.add(String.valueOf(bowliplnoballs));
                    playerIplBowlArrayList.add(String.format("%.2f",bowlipl_sr));
                    playerIplBowlArrayList.add(String.format("%.2f",bowlipl_avg));
                    playerIplBowlArrayList.add(String.format("%.2f",bowlipl_eco));
                    playerIplBowlArrayList.add(String.valueOf(bowliplfour));
                    playerIplBowlArrayList.add(String.valueOf(bowliplfive));
                    playerIplBowlArrayList.add(String.valueOf(bowliplten));

                    // Bowling Arrays Ends

                    setupViewPager(view_pager);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Player",e.getMessage().toString());
                    Toast.makeText(PlayerDetailActivity.this, "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
                pDialog.hide();

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }


                //    Toast.makeText(getContext(), message + "income", Toast.LENGTH_LONG).show();

                pDialog.hide();
            }
        }) {

        };
        strReq1.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        strReq1.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq1, tag_string_req1);
    }

    private void setupViewPager(ViewPager viewPager) {

        PlayerDetailPagerAdapter adapter = new PlayerDetailPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PlayerInfo.newInstance(playerInfoArrayList), "Info");

        adapter.addFragment(PlayerBattingCareer.newInstance(battingHeaderArray,playerTestBatArrayList,playerOdiBatArrayList,playerT20BatArrayList,playerIplBatArrayList), "Batting");
        adapter.addFragment(PlayerBowlingCareer.newInstance(bowlHeaderArray,playerTestBowlArrayList,playerOdiBowlArrayList,playerT20BowlArrayList,playerIplBowlArrayList), "Bowling");
        viewPager.setAdapter(adapter);
    }

    private class PlayerDetailPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public PlayerDetailPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
