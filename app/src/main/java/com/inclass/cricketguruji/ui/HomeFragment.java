package com.inclass.cricketguruji.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

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
import com.inclass.cricketguruji.Activities.PlayersActivity;
import com.inclass.cricketguruji.Adapters.HomePageAdapter;
import com.inclass.cricketguruji.AppController;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.LiveMatch;
import com.inclass.cricketguruji.model.PlayerConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View root;
    Activity activity;
    public LiveMatch liveMatch;
    public ArrayList<LiveMatch> liveMatchArrayList = new ArrayList<LiveMatch>();
    HomePageAdapter homePageAdapter;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        makeJsonArrayRequest();
        return root;
    }

    private void makeJsonArrayRequest() {
        String tag_string_req1 = "string_req";
        String urlJsonArry = "https://cricket.sportmonks.com/api/v2.0/fixtures?api_token=bq9QWLmC7ykuVHn1GWdogPmaWy4Swn3Q7n2V3PBpTlang6CQf4RqRwcLGBvG" +
                "&include=localteam,visitorteam,stage&filter[status]=NS";
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                    JSONArray match = jsonObject.getJSONArray("data");
                    for (int i = 0; i < match.length(); i++) {
                        liveMatch = new LiveMatch();
                        JSONObject jsonObject1 = match.getJSONObject(i);
                        liveMatch.matchid = jsonObject1.getString("id");
                        liveMatch.localteam = jsonObject1.getJSONObject("localteam").getString("name");
                        liveMatch.localteam_image = jsonObject1.getJSONObject("localteam").getString("image_path");
                        liveMatch.visitorteam = jsonObject1.getJSONObject("visitorteam").getString("name");
                        liveMatch.visitorTeam_image = jsonObject1.getJSONObject("visitorteam").getString("image_path");
                        liveMatch.season_name = jsonObject1.getJSONObject("stage").getString("name");
                        liveMatchArrayList.add(liveMatch);
                    }

                    homePageAdapter = new HomePageAdapter(liveMatchArrayList,getActivity());
                    viewPager = root.findViewById(R.id.viewPager);
                    viewPager.setAdapter(homePageAdapter);
//                    viewPager.setPadding(130,0,130,0);

                    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
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
}
