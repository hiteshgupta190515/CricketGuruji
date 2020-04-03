package com.inclass.cricketguruji.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.inclass.cricketguruji.AppController;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.TeamsModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DomesticTeams extends Fragment {

    View rootView;
    private ProgressDialog pDialog;
    public ArrayList<TeamsModel> teamsModelArrayList = new ArrayList<TeamsModel>();
    ArrayList<TeamsModel> noRepeat = new ArrayList<TeamsModel>();
    TeamsModel teamsModel;
    TeamsAdapter listAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Activity activity;
    RecyclerView teamsRecycler;

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

    public static DomesticTeams newInstance() {
        DomesticTeams fragment = new DomesticTeams();
        Bundle args = new Bundle();
        return fragment;
    }

    public DomesticTeams() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_teams, container, false);

        teamsRecycler = rootView.findViewById(R.id.teamsRecycler);
        makeJsonArrayRequest();
        return rootView;
    }


    private void makeJsonArrayRequest() {
        String tag_string_req1 = "string_req";
        String urlJsonArry = "https://cricket.sportmonks.com/api/v2.0/teams?api_token=bq9QWLmC7ykuVHn1GWdogPmaWy4Swn3Q7n2V3PBpTlang6CQf4RqRwcLGBvG";
        // Defined Array values to show in ListView
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq1 = new StringRequest(Request.Method.GET,
                urlJsonArry, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Teams", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray matches = jsonObject.getJSONArray("data");
                    for (int i = 0; i < matches.length(); i++) {
                        teamsModel = new TeamsModel();
                        JSONObject jsonObject1 = matches.getJSONObject(i);
                        if (jsonObject1.getString("national_team").equals("false")) {
                                    teamsModel.setId(jsonObject1.getString("id"));
                                    teamsModel.setName(jsonObject1.getString("name"));
                                    teamsModel.setCode(jsonObject1.getString("code"));
                                    teamsModel.setImage(jsonObject1.getString("image_path"));
                                    teamsModel.setCountryid(jsonObject1.getString("country_id"));
                                    teamsModel.setNationalteam(jsonObject1.getString("national_team"));
                                    teamsModelArrayList.add(teamsModel);
                        }
                    }
                    for (TeamsModel teams : teamsModelArrayList) {
                        boolean isFound = false;
                        // check if the teams id exists in noRepeat
                        for (TeamsModel e : noRepeat) {
                            if (e.getId().equals(teams.getId()) || (e.equals(teams))) {
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) noRepeat.add(teams);
                    }
                    TeamsAdapter pendingListAdapter = new TeamsAdapter(getActivity(), noRepeat);
                    teamsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    teamsRecycler.setAdapter(pendingListAdapter);
                    pendingListAdapter.notifyDataSetChanged();
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


    private class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> {

        Context ctx;
        LayoutInflater infalter;
        private ArrayList<TeamsModel> teamsModels;

        TeamsAdapter(Context context, ArrayList<TeamsModel> teamsModels) {
            this.teamsModels = teamsModels;
            this.ctx = context;
            this.infalter = LayoutInflater.from(getActivity());
        }

        @NonNull
        @Override
        public TeamsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = infalter.inflate(R.layout.item_teams, parent, false);

            return new TeamsAdapter.MyViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull TeamsAdapter.MyViewHolder holder, final int position) {
            final TeamsModel p = teamsModels.get(position);
            Picasso.with(ctx).load(p.getImage()).into(holder.img_datapath);
            holder.name.setText(p.getName());
        }

        @Override
        public int getItemCount() {
            return teamsModels.size();

        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView img_datapath;
            TextView name;

            MyViewHolder(View itemView) {
                super(itemView);
                img_datapath = itemView.findViewById(R.id.team1_img);
                name = itemView.findViewById(R.id.name);
            }
        }
    }
}
