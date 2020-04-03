package com.inclass.cricketguruji.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.inclass.cricketguruji.AppController;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.PlayerConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersActivity extends AppCompatActivity {

    public PlayerConstants playerConstants;
    public ArrayList<PlayerConstants> playerConstantsArrayList = new ArrayList<PlayerConstants>();
    RecyclerView playerList;
    Toolbar toolbar;
    EditText search_layout;
    PlayerAdapter playerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Players");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        playerList = findViewById(R.id.playerList);
        search_layout = findViewById(R.id.search_layout);
        makeJsonArrayRequest();

        search_layout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
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

    private void filter(String text) {
        ArrayList<PlayerConstants> filteredList = new ArrayList<>();

        for (PlayerConstants item : playerConstantsArrayList) {
            if (item.fullname.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }
        }
        Log.e("Filter", String.valueOf(filteredList));
        playerAdapter.filterList(filteredList);
        playerAdapter.notifyDataSetChanged();
    }

    private void makeJsonArrayRequest() {
        String tag_string_req1 = "string_req";
        String urlJsonArry = "https://cricket.sportmonks.com/api/v2.0/players?api_token=bq9QWLmC7ykuVHn1GWdogPmaWy4Swn3Q7n2V3PBpTlang6CQf4RqRwcLGBvG";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest strReq1 = new StringRequest(Request.Method.GET,
                urlJsonArry, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Players", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray players = jsonObject.getJSONArray("data");
                    for (int i = 0; i < players.length(); i++) {
                        playerConstants = new PlayerConstants();
                        JSONObject jsonObject1 = players.getJSONObject(i);
                            playerConstants.playerid = jsonObject1.getString("id");
                            playerConstants.fullname = jsonObject1.getString("fullname");
                            playerConstants.image_path = jsonObject1.getString("image_path");
                            playerConstantsArrayList.add(playerConstants);
                    }

                    playerAdapter = new PlayerAdapter(PlayersActivity.this, playerConstantsArrayList);
                    playerList.setLayoutManager(new LinearLayoutManager(PlayersActivity.this, LinearLayoutManager.VERTICAL, false));
                    playerList.setAdapter(playerAdapter);
                    playerAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PlayersActivity.this, "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
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

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {

        Context ctx;
        LayoutInflater infalter;
        private ArrayList<PlayerConstants> teamsModels;

        PlayerAdapter(Context context, ArrayList<PlayerConstants> teamsModels) {
            this.teamsModels = teamsModels;
            this.ctx = context;
            this.infalter = LayoutInflater.from(getApplicationContext());
        }

        @NonNull
        @Override
        public PlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = infalter.inflate(R.layout.item_playerlist, parent, false);

            return new PlayerAdapter.MyViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerAdapter.MyViewHolder holder, final int position) {
            final PlayerConstants p = teamsModels.get(position);
            Picasso.with(ctx).load(p.image_path).placeholder(R.drawable.logo).into(holder.player_img);
            holder.player_name.setText(p.fullname);
            holder.player_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx,PlayerDetailActivity.class);
                    intent.putExtra("playerid",p.playerid);
                    intent.putExtra("playername",p.fullname);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return teamsModels.size();

        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            CircleImageView player_img;
            TextView player_name;

            MyViewHolder(View itemView) {
                super(itemView);
                player_img = itemView.findViewById(R.id.player_img);
                player_name = itemView.findViewById(R.id.player_name);
            }
        }

        public void filterList(ArrayList<PlayerConstants> filteredList) {
            teamsModels = filteredList;
            notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
