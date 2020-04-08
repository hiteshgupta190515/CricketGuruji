package com.inclass.cricketguruji.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.inclass.cricketguruji.Adapters.RecentMatches_StageAdapters;
import com.inclass.cricketguruji.AppController;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.Constants;
import com.inclass.cricketguruji.model.StageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecentMatchesFragment extends Fragment {

    Activity activity;
    private View root;
    private RecyclerView matches_recyclerView;
    Constants constants;
    StageModel stageModel;
    public ArrayList<Constants> upcoming_match_list = new ArrayList<Constants>();
    public ArrayList<StageModel> stage_list = new ArrayList<StageModel>();
    public RecentMatches_StageAdapters stageAdapters;
    public ArrayList<StageModel> noRepeatStage = new ArrayList<StageModel>();
    public ArrayList<StageModel> finalfixtures = new ArrayList<StageModel>();
    String currentdate;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static RecentMatchesFragment newInstance() {
        RecentMatchesFragment fragment = new RecentMatchesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initComponent();
        makeJsonArrayRequest();
        return root;
    }

    private void initComponent() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentdate = df.format(c);
        matches_recyclerView = (RecyclerView) root.findViewById(R.id.matches_recyclerView);
        matches_recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        matches_recyclerView.setHasFixedSize(true);

        stageAdapters = new RecentMatches_StageAdapters(activity, this);
        matches_recyclerView.setAdapter(stageAdapters);
    }

    private void makeJsonArrayRequest() {
        String tag_string_req1 = "string_req";
        String urlJsonArry =
                "https://cricket.sportmonks.com/api/v2.0/fixtures?api_token=bq9QWLmC7ykuVHn1GWdogPmaWy4Swn3Q7n2V3PBpTlang6CQf4RqRwcLGBvG&include=stage,league,visitorteam,localteam,venue&filter[starts_between]=2020-01-01,"+currentdate+"&sort=starting_at&filter[status]=Finished,Postp.,Int.,Aban.,Delayed,Cancl.";
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq1 = new StringRequest(Request.Method.GET,
                urlJsonArry, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Matches", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray matches = jsonObject.getJSONArray("data");
                    for (int i = 0; i < matches.length(); i++) {
                        stageModel = new StageModel();
                        JSONObject jsonObject1 = matches.getJSONObject(i);
                            stageModel.stageid = jsonObject1.getJSONObject("stage").getString("id");
                            stageModel.stageName = jsonObject1.getJSONObject("stage").getString("name");
                            stage_list.add(stageModel);

                    }

                    for (StageModel stages : stage_list) {
                        boolean isFound = false;
                        // check if the teams id exists in noRepeat
                        for (StageModel e : noRepeatStage) {
                            if (e.stageid.equals(stages.stageid) || (e.equals(stages))) {
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) {
                            noRepeatStage.add(stages);
                        }
                    }
                    int counter_for_hitesh = 0;
                    for (int j = 0; j < noRepeatStage.size(); j++) {
                        StageModel dm = new StageModel();
                        upcoming_match_list = new ArrayList<Constants>();
                        for (int k = 0; k < matches.length(); k++) {
                            constants = new Constants();
                            JSONObject jsonObject2 = matches.getJSONObject(k);
//                            for (int h = 0; h < hitesh.size(); h++) {
//                                if (hitesh.get(h).equals(jsonObject2.getJSONObject("stage").getString("id"))) {
//                                    counter_for_hitesh++;
//                                }
//                            }
                                if ((noRepeatStage.get(j).stageid.equals(jsonObject2.getJSONObject("stage").getString("id")))) {

                                    constants.matchId = jsonObject2.getString("id");
                                    constants.round = jsonObject2.getString("round");
                                    constants.localteam = jsonObject2.getJSONObject("localteam").getString("name");
                                    constants.localimage = jsonObject2.getJSONObject("localteam").getString("image_path");
                                    constants.visitorteam = jsonObject2.getJSONObject("visitorteam").getString("name");
                                    constants.visitorimage = jsonObject2.getJSONObject("visitorteam").getString("image_path");
                                    constants.startingdate = jsonObject2.getString("starting_at").substring(0, 10);
                                    constants.startingtime = jsonObject2.getString("starting_at").substring(12, 16);
                                    constants.stageid = jsonObject2.getJSONObject("stage").getString("id");
                                    constants.stageName = jsonObject2.getJSONObject("stage").getString("name");
                                    upcoming_match_list.add(constants);
                                }

                        }

                        dm.setAllItemsInSection(upcoming_match_list);

                        finalfixtures.add(dm);

                    }


                    stageAdapters.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq1, tag_string_req1);
    }
}
