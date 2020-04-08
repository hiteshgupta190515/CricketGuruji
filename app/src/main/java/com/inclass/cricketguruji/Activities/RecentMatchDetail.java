package com.inclass.cricketguruji.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.inclass.cricketguruji.Fragments.IniningsScoreboard;
import com.inclass.cricketguruji.Fragments.MatchInfo;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.ScoreBoard;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecentMatchDetail extends AppCompatActivity {

    Toolbar toolbar;
    String match_id,localteam_id,visitorteam_id,localteam_name,visitorteam_name,localteam_code,visitorteam_code;
    TextView team_name,match_note,team1_name,team1_score,team2_name,team2_score,
            team1_name1,team1_score1,team2_name2,team2_score2,title;
    ImageView team_img;
    LinearLayout winnerLayout,ininings34_layout;

    private ViewPager view_pager;
    private TabLayout tab_layout;
    int ininingCount;
    ScoreBoard scoreBoardModel;
    public ArrayList<ScoreBoard> s1scoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> s2scoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> s3scoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> s4scoreBoardArrayList = new ArrayList<ScoreBoard>();

    public ArrayList<ScoreBoard> s1bowlscoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> s2bowlscoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> s3bowlscoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> s4bowlscoreBoardArrayList = new ArrayList<ScoreBoard>();

    public ArrayList<ScoreBoard> localteamLineup = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> visitorteamLineup = new ArrayList<ScoreBoard>();

    private String venuename,venuecity,venuecapacity,venueflood,venueimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_match_detail);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                match_id= null;
            } else {
                match_id= extras.getString("match_id");
            }
        } else {
            match_id= (String) savedInstanceState.getSerializable("match_id");
        }
        Log.e("MatchID",match_id);
        initComponent();
        makeJsonArrayRequest();
    }

    private void initComponent(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Match Info");
        team_img = findViewById(R.id.team_img);
        team_name = findViewById(R.id.team_name);
        match_note = findViewById(R.id.match_note);
        team1_name = findViewById(R.id.team1_name);
        team1_score = findViewById(R.id.team1_score);
        team2_name = findViewById(R.id.team2_name);
        team2_score = findViewById(R.id.team2_score);
        winnerLayout = findViewById(R.id.winnerLayout);
        ininings34_layout = findViewById(R.id.ininings34_layout);
        team1_name1 = findViewById(R.id.team1_name1);
        team1_score1 = findViewById(R.id.team1_score1);
        team2_name2 = findViewById(R.id.team2_name2);
        team2_score2 = findViewById(R.id.team2_score2);
        title = findViewById(R.id.title);

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        tab_layout = (TabLayout) findViewById(R.id.tabs);

        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ScoreBoardAdapter adapter = new ScoreBoardAdapter(getSupportFragmentManager());
        Log.e("IniningsCount", String.valueOf(ininingCount));
        if(ininingCount>2){
            adapter.addFragment(MatchInfo.newInstance(localteamLineup,visitorteamLineup,localteam_name,visitorteam_name,
                    venuename,venuecity,venuecapacity,venueflood,venueimage), "Info");
            adapter.addFragment(IniningsScoreboard.newInstance(s1scoreBoardArrayList,s1bowlscoreBoardArrayList), "1st Ininings");
            adapter.addFragment(IniningsScoreboard.newInstance(s2scoreBoardArrayList,s2bowlscoreBoardArrayList), "2nd Ininings");
            adapter.addFragment(IniningsScoreboard.newInstance(s3scoreBoardArrayList,s3bowlscoreBoardArrayList), "3rd Ininings");
            adapter.addFragment(IniningsScoreboard.newInstance(s4scoreBoardArrayList,s4bowlscoreBoardArrayList), "4th Ininings");
        } else{
            adapter.addFragment(MatchInfo.newInstance(localteamLineup,visitorteamLineup,localteam_name,visitorteam_name,
                    venuename,venuecity,venuecapacity,venueflood,venueimage), "Info");
            adapter.addFragment(IniningsScoreboard.newInstance(s1scoreBoardArrayList,s1bowlscoreBoardArrayList), "1st Ininings");
            adapter.addFragment(IniningsScoreboard.newInstance(s2scoreBoardArrayList,s2bowlscoreBoardArrayList), "2nd Ininings");
        }

        viewPager.setAdapter(adapter);
    }

    private void makeJsonArrayRequest() {
        String tag_string_req1 = "string_req";
        String urlJsonArry = "https://cricket.sportmonks.com/api/v2.0/fixtures/"+match_id+"?api_token=bq9QWLmC7ykuVHn1GWdogPmaWy4Swn3Q7n2V3PBpTlang6CQf4RqRwcLGBvG" +
                "&include=localteam,visitorteam,scoreboards,runs,batting,bowling,batting.batsman,batting.bowler,batting.catchstump,bowling.bowler,,batting.result,lineup,venue";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest strReq1 = new StringRequest(Request.Method.GET,
                urlJsonArry, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Matches", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    localteam_id = jsonObject.getJSONObject("data").getString("localteam_id");
                    localteam_name = jsonObject.getJSONObject("data").getJSONObject("localteam").getString("name");
                    localteam_code = jsonObject.getJSONObject("data").getJSONObject("localteam").getString("code");
                    visitorteam_id = jsonObject.getJSONObject("data").getString("visitorteam_id");
                    visitorteam_name = jsonObject.getJSONObject("data").getJSONObject("visitorteam").getString("name");
                    visitorteam_code = jsonObject.getJSONObject("data").getJSONObject("visitorteam").getString("code");
                    ininingCount = jsonObject.getJSONObject("data").getJSONArray("runs").length();
                    if(jsonObject.getJSONObject("data").getString("winner_team_id").equals(jsonObject.getJSONObject("data").getJSONObject("localteam").getString("id"))){
                        Picasso.with(getApplicationContext()).load(jsonObject.getJSONObject("data").getJSONObject("localteam").getString("image_path")).placeholder(R.drawable.flag_avatar).into(team_img);
                        team_name.setText(jsonObject.getJSONObject("data").getJSONObject("localteam").getString("name"));
                    } else if(jsonObject.getJSONObject("data").getString("winner_team_id").equals(jsonObject.getJSONObject("data").getJSONObject("visitorteam").getString("id"))){
                        Picasso.with(getApplicationContext()).load(jsonObject.getJSONObject("data").getJSONObject("visitorteam").getString("image_path")).placeholder(R.drawable.flag_avatar).into(team_img);
                        team_name.setText(jsonObject.getJSONObject("data").getJSONObject("visitorteam").getString("name"));
                    } else{
                        winnerLayout.setVisibility(View.GONE);
                        match_note.setTextSize(20);
                    }
                    match_note.setText(jsonObject.getJSONObject("data").getString("note"));
                    team1_name.setText(localteam_code);
                    team2_name.setText(visitorteam_code);
                    JSONArray scoreboard = jsonObject.getJSONObject("data").getJSONArray("scoreboards");
                    for (int i=0;i<scoreboard.length();i++){
                        JSONObject jsonObject1 = scoreboard.getJSONObject(i);

                        if(jsonObject1.getString("type").equals("total")){
                            if (scoreboard.length()<4){
                                if(localteam_id.equals(jsonObject1.getString("team_id"))){
                                    team1_score.setText(jsonObject1.getString("total")+"/"+
                                            jsonObject1.getString("wickets") + " (" +
                                            jsonObject1.getString("overs") + ")");
                                } else {
                                    team1_score.setText("DNB");
                                }

                                 if(visitorteam_id.equals(jsonObject1.getString("team_id"))){
                                    team2_score.setText(jsonObject1.getString("total")+"/"+
                                            jsonObject1.getString("wickets") + " (" +
                                            jsonObject1.getString("overs") + ")");
                                } else {
                                     team2_score.setText("DNB");
                                 }
                            }else {
                                if (localteam_id.equals(jsonObject1.getString("team_id")) &&
                                        ( jsonObject1.getString("scoreboard").equals("S1") ||
                                                jsonObject1.getString("scoreboard").equals("S2") )) {
                                    team1_score.setText(jsonObject1.getString("total") + "/" +
                                            jsonObject1.getString("wickets") + " (" +
                                            jsonObject1.getString("overs") + ")");
                                } else if (visitorteam_id.equals(jsonObject1.getString("team_id")) &&
                                        ( jsonObject1.getString("scoreboard").equals("S1") ||
                                                jsonObject1.getString("scoreboard").equals("S2") )) {
                                    team2_score.setText(jsonObject1.getString("total") + "/" +
                                            jsonObject1.getString("wickets") + " (" +
                                            jsonObject1.getString("overs") + ")");
                                }

                                if (localteam_id.equals(jsonObject1.getString("team_id")) &&
                                        ( jsonObject1.getString("scoreboard").equals("S3") ||
                                                jsonObject1.getString("scoreboard").equals("S4") )) {
                                    ininings34_layout.setVisibility(View.VISIBLE);
                                    if(jsonObject.getJSONObject("data").getString("super_over").equals("true")){
                                        title.setVisibility(View.VISIBLE);
                                    }
                                    team1_name1.setText(localteam_code);
                                    team1_score1.setText(jsonObject1.getString("total") + "/" +
                                            jsonObject1.getString("wickets") + " (" +
                                            jsonObject1.getString("overs") + ")");
                                } else if (visitorteam_id.equals(jsonObject1.getString("team_id")) &&
                                        ( jsonObject1.getString("scoreboard").equals("S3") ||
                                                jsonObject1.getString("scoreboard").equals("S4") )) {
                                    ininings34_layout.setVisibility(View.VISIBLE);
                                    if(jsonObject.getJSONObject("data").getString("super_over").equals("true")){
                                        title.setVisibility(View.VISIBLE);
                                    }
                                    team2_name2.setText(visitorteam_code);
                                    team2_score2.setText(jsonObject1.getString("total") + "/" +
                                            jsonObject1.getString("wickets") + " (" +
                                            jsonObject1.getString("overs") + ")");
                                }
                            }
                        }
                    }

                    JSONArray batting = jsonObject.getJSONObject("data").getJSONArray("batting");
                    for (int i=0;i<batting.length();i++) {
                        JSONObject jsonObject2 = batting.getJSONObject(i);
                        scoreBoardModel = new ScoreBoard();
                        if(jsonObject2.getString("scoreboard").equals("S1")){
                            if((!jsonObject2.isNull("batsman"))){
                                scoreBoardModel.batsman = jsonObject2.getJSONObject("batsman").getString("fullname");
                            }
                            if((!jsonObject2.isNull("bowler"))){
                                scoreBoardModel.bowlerout = jsonObject2.getJSONObject("bowler").getString("lastname");
                            }
                            if((!jsonObject2.isNull("catchstump"))){
                                scoreBoardModel.catchout = jsonObject2.getJSONObject("catchstump").getString("lastname");
                            }
                            if((!jsonObject2.isNull("result"))){
                                scoreBoardModel.outresult = jsonObject2.getJSONObject("result").getString("name");
                            }
                            if (localteam_id.equals(jsonObject2.getString("team_id"))){
                                scoreBoardModel.battingteam = localteam_code;
                            }else {
                                scoreBoardModel.battingteam = visitorteam_code;
                            }
                            scoreBoardModel.runs = jsonObject2.getString("score");
                            scoreBoardModel.balls = jsonObject2.getString("ball");
                            scoreBoardModel.four = jsonObject2.getString("four_x");
                            scoreBoardModel.six = jsonObject2.getString("six_x");
                            scoreBoardModel.sr = jsonObject2.getString("rate");
                            s1scoreBoardArrayList.add(scoreBoardModel);

                        }
                        if(jsonObject2.getString("scoreboard").equals("S2")){
                            if((!jsonObject2.isNull("batsman"))){
                                scoreBoardModel.batsman = jsonObject2.getJSONObject("batsman").getString("fullname");
                            }
                            if((!jsonObject2.isNull("bowler"))){
                                scoreBoardModel.bowlerout = jsonObject2.getJSONObject("bowler").getString("lastname");
                            }
                            if((!jsonObject2.isNull("catchstump"))){
                                scoreBoardModel.catchout = jsonObject2.getJSONObject("catchstump").getString("lastname");
                            }
                            if((!jsonObject2.isNull("result"))){
                                scoreBoardModel.outresult = jsonObject2.getJSONObject("result").getString("name");
                            }
                            if (localteam_id.equals(jsonObject2.getString("team_id"))){
                                scoreBoardModel.battingteam = localteam_code;
                            }else {
                                scoreBoardModel.battingteam = visitorteam_code;
                            }
                            scoreBoardModel.runs = jsonObject2.getString("score");
                            scoreBoardModel.balls = jsonObject2.getString("ball");
                            scoreBoardModel.four = jsonObject2.getString("four_x");
                            scoreBoardModel.six = jsonObject2.getString("six_x");
                            scoreBoardModel.sr = jsonObject2.getString("rate");
                            s2scoreBoardArrayList.add(scoreBoardModel);
                        }

                        if(jsonObject2.getString("scoreboard").equals("S3")){
                            if((!jsonObject2.isNull("batsman"))){
                                scoreBoardModel.batsman = jsonObject2.getJSONObject("batsman").getString("fullname");
                            }
                            if((!jsonObject2.isNull("bowler"))){
                                scoreBoardModel.bowlerout = jsonObject2.getJSONObject("bowler").getString("lastname");
                            }
                            if((!jsonObject2.isNull("catchstump"))){
                                scoreBoardModel.catchout = jsonObject2.getJSONObject("catchstump").getString("lastname");
                            }
                            if((!jsonObject2.isNull("result"))){
                                scoreBoardModel.outresult = jsonObject2.getJSONObject("result").getString("name");
                            }
                            if (localteam_id.equals(jsonObject2.getString("team_id"))){
                                scoreBoardModel.battingteam = localteam_code;
                            }else {
                                scoreBoardModel.battingteam = visitorteam_code;
                            }
                            scoreBoardModel.runs = jsonObject2.getString("score");
                            scoreBoardModel.balls = jsonObject2.getString("ball");
                            scoreBoardModel.four = jsonObject2.getString("four_x");
                            scoreBoardModel.six = jsonObject2.getString("six_x");
                            scoreBoardModel.sr = jsonObject2.getString("rate");
                            s3scoreBoardArrayList.add(scoreBoardModel);
                        }

                        if(jsonObject2.getString("scoreboard").equals("S4")){
                            if((!jsonObject2.isNull("batsman"))){
                                scoreBoardModel.batsman = jsonObject2.getJSONObject("batsman").getString("fullname");
                            }
                            if((!jsonObject2.isNull("bowler"))){
                                scoreBoardModel.bowlerout = jsonObject2.getJSONObject("bowler").getString("lastname");
                            }
                            if((!jsonObject2.isNull("catchstump"))){
                                scoreBoardModel.catchout = jsonObject2.getJSONObject("catchstump").getString("lastname");
                            }
                            if((!jsonObject2.isNull("result"))){
                                scoreBoardModel.outresult = jsonObject2.getJSONObject("result").getString("name");
                            }
                            if (localteam_id.equals(jsonObject2.getString("team_id"))){
                                scoreBoardModel.battingteam = localteam_code;
                            }else {
                                scoreBoardModel.battingteam = visitorteam_code;
                            }
                            scoreBoardModel.runs = jsonObject2.getString("score");
                            scoreBoardModel.balls = jsonObject2.getString("ball");
                            scoreBoardModel.four = jsonObject2.getString("four_x");
                            scoreBoardModel.six = jsonObject2.getString("six_x");
                            scoreBoardModel.sr = jsonObject2.getString("rate");
                            s4scoreBoardArrayList.add(scoreBoardModel);
                        }
                    }

                    JSONArray bowling = jsonObject.getJSONObject("data").getJSONArray("bowling");
                    for (int i=0;i<bowling.length();i++) {
                        JSONObject jsonObject3 = bowling.getJSONObject(i);
                        scoreBoardModel = new ScoreBoard();
                        if(jsonObject3.getString("scoreboard").equals("S1")){
                            if((!jsonObject3.isNull("bowler"))){
                                scoreBoardModel.bowler = jsonObject3.getJSONObject("bowler").getString("fullname");
                            }
                            if (localteam_id.equals(jsonObject3.getString("team_id"))){
                                scoreBoardModel.bowlingteam = localteam_code;
                            }else {
                                scoreBoardModel.bowlingteam = visitorteam_code;
                            }
                            scoreBoardModel.overs = jsonObject3.getString("overs");
                            scoreBoardModel.madiens = jsonObject3.getString("medians");
                            scoreBoardModel.bowlerruns = jsonObject3.getString("runs");
                            scoreBoardModel.wickets = jsonObject3.getString("wickets");
                            scoreBoardModel.wide = jsonObject3.getString("wide");
                            scoreBoardModel.noballs = jsonObject3.getString("noball");
                            s1bowlscoreBoardArrayList.add(scoreBoardModel);
                        }
                        if(jsonObject3.getString("scoreboard").equals("S2")){
                            if((!jsonObject3.isNull("bowler"))){
                                scoreBoardModel.bowler = jsonObject3.getJSONObject("bowler").getString("fullname");
                            }
                            if (localteam_id.equals(jsonObject3.getString("team_id"))){
                                scoreBoardModel.bowlingteam = localteam_code;
                            }else {
                                scoreBoardModel.bowlingteam = visitorteam_code;
                            }
                            scoreBoardModel.overs = jsonObject3.getString("overs");
                            scoreBoardModel.madiens = jsonObject3.getString("medians");
                            scoreBoardModel.bowlerruns = jsonObject3.getString("runs");
                            scoreBoardModel.wickets = jsonObject3.getString("wickets");
                            scoreBoardModel.wide = jsonObject3.getString("wide");
                            scoreBoardModel.noballs = jsonObject3.getString("noball");
                            s2bowlscoreBoardArrayList.add(scoreBoardModel);
                        }
                        if(jsonObject3.getString("scoreboard").equals("S3")){
                            if((!jsonObject3.isNull("bowler"))){
                                scoreBoardModel.bowler = jsonObject3.getJSONObject("bowler").getString("fullname");
                            }
                            if (localteam_id.equals(jsonObject3.getString("team_id"))){
                                scoreBoardModel.bowlingteam = localteam_code;
                            }else {
                                scoreBoardModel.bowlingteam = visitorteam_code;
                            }
                            scoreBoardModel.overs = jsonObject3.getString("overs");
                            scoreBoardModel.madiens = jsonObject3.getString("medians");
                            scoreBoardModel.bowlerruns = jsonObject3.getString("runs");
                            scoreBoardModel.wickets = jsonObject3.getString("wickets");
                            scoreBoardModel.wide = jsonObject3.getString("wide");
                            scoreBoardModel.noballs = jsonObject3.getString("noball");
                            s3bowlscoreBoardArrayList.add(scoreBoardModel);
                        }
                        if(jsonObject3.getString("scoreboard").equals("S4")){
                            if((!jsonObject3.isNull("bowler"))){
                                scoreBoardModel.bowler = jsonObject3.getJSONObject("bowler").getString("fullname");
                            }
                            if (localteam_id.equals(jsonObject3.getString("team_id"))){
                                scoreBoardModel.bowlingteam = localteam_code;
                            }else {
                                scoreBoardModel.bowlingteam = visitorteam_code;
                            }
                            scoreBoardModel.overs = jsonObject3.getString("overs");
                            scoreBoardModel.madiens = jsonObject3.getString("medians");
                            scoreBoardModel.bowlerruns = jsonObject3.getString("runs");
                            scoreBoardModel.wickets = jsonObject3.getString("wickets");
                            scoreBoardModel.wide = jsonObject3.getString("wide");
                            scoreBoardModel.noballs = jsonObject3.getString("noball");
                            s4bowlscoreBoardArrayList.add(scoreBoardModel);
                        }
                    }

                    JSONArray lineup = jsonObject.getJSONObject("data").getJSONArray("lineup");
                    for (int i=0;i<lineup.length();i++) {
                        JSONObject jsonObject4 = lineup.getJSONObject(i);
                        scoreBoardModel = new ScoreBoard();
                        if(localteam_id.equals(jsonObject4.getJSONObject("lineup").getString("team_id"))){
                            scoreBoardModel.playername = jsonObject4.getString("fullname");
                            scoreBoardModel.captian = jsonObject4.getJSONObject("lineup").getString("captain");
                            scoreBoardModel.wk = jsonObject4.getJSONObject("lineup").getString("wicketkeeper");
                            localteamLineup.add(scoreBoardModel);
                        } else if(visitorteam_id.equals(jsonObject4.getJSONObject("lineup").getString("team_id"))){
                            scoreBoardModel.playername = jsonObject4.getString("fullname");
                            scoreBoardModel.captian = jsonObject4.getJSONObject("lineup").getString("captain");
                            scoreBoardModel.wk = jsonObject4.getJSONObject("lineup").getString("wicketkeeper");
                            visitorteamLineup.add(scoreBoardModel);
                        }

                    }

                    venueimage = jsonObject.getJSONObject("data").getJSONObject("venue").getString("image_path");
                    venuename = jsonObject.getJSONObject("data").getJSONObject("venue").getString("name");
                    venuecity = jsonObject.getJSONObject("data").getJSONObject("venue").getString("city");
                    venuecapacity = jsonObject.getJSONObject("data").getJSONObject("venue").getString("capacity");
                    venueflood = jsonObject.getJSONObject("data").getJSONObject("venue").getString("floodlight");

                    setupViewPager(view_pager);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
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
        AppController.getInstance().addToRequestQueue(strReq1, tag_string_req1);
    }

    static class ScoreBoardAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ScoreBoardAdapter(FragmentManager manager) {
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
}
