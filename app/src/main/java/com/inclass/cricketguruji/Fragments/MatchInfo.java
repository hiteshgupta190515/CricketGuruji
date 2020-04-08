package com.inclass.cricketguruji.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.ScoreBoard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchInfo extends Fragment {

    View root;
    Activity activity;
    public ArrayList<ScoreBoard> localteam_player = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> vistorteam_players = new ArrayList<ScoreBoard>();
    RecyclerView localteam_players,visitorteam_players;
    LocalTeamAdapter localTeamAdapter,visitorTeamAdapter;
    TextView localteam,visitorteam,venuename,venuecity,venuecapacity,venueflood;
    String localteamName,visitorteamName,venueName,venueCity,venueCapacity,venueFlood,venueImage;
    ImageView venueimage;
    
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

    public static MatchInfo newInstance(ArrayList<ScoreBoard> localteam_players, ArrayList<ScoreBoard> vistorteam_players,
                                        String localteam, String visitorteam, String venueName, String venueCity,
                                        String venueCapacity, String venueFlood,String venueImage) {
        MatchInfo fragment = new MatchInfo();
        Bundle args = new Bundle();
        args.putSerializable("localteam_players", localteam_players);
        args.putSerializable("vistorteam_players", vistorteam_players);
        args.putString("localteamName", localteam);
        args.putString("visitorteamName", visitorteam);
        args.putString("venueName", venueName);
        args.putString("venueCity", venueCity);
        args.putString("venueCapacity", venueCapacity);
        args.putString("venueFlood", venueFlood);
        args.putString("venueImage", venueImage);
        fragment.setArguments(args);
        return fragment;
    }

    public MatchInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localteam_player = (ArrayList<ScoreBoard>)getArguments().getSerializable("localteam_players");
        vistorteam_players = (ArrayList<ScoreBoard>)getArguments().getSerializable("vistorteam_players");
        localteamName = getArguments().getString("localteamName");
        visitorteamName = getArguments().getString("visitorteamName");
        venueName = getArguments().getString("venueName");
        venueCity = getArguments().getString("venueCity");
        venueCapacity = getArguments().getString("venueCapacity");
        venueFlood = getArguments().getString("venueFlood");
        venueImage = getArguments().getString("venueImage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_matchinfo, container, false);

        localteam = root.findViewById(R.id.localteam);
        visitorteam = root.findViewById(R.id.visitorteam);
        localteam.setText(localteamName);
        visitorteam.setText(visitorteamName);

        venuename = root.findViewById(R.id.venuename);
        venuecity = root.findViewById(R.id.venuecity);
        venuecapacity = root.findViewById(R.id.venuecapacity);
        venueflood = root.findViewById(R.id.venueflood);
        venueimage = root.findViewById(R.id.venueimage);
        venuename.setText("Name: "+venueName);
        venuecity.setText("City: "+venueCity);
        venuecapacity.setText("Capacity: "+venueCapacity);
        if(venueFlood.equals("true"))
            venueflood.setText("FloodLights: Yes");
        else
            venueflood.setText("FloodLights: No");
        Picasso.with(activity).load(venueImage).into(venueimage);

        localteam_players = root.findViewById(R.id.localteam_players);
        localTeamAdapter = new LocalTeamAdapter(getActivity(), localteam_player);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        localteam_players.setLayoutManager(mLayoutManager);
        localteam_players.setItemAnimator(new DefaultItemAnimator());
        localteam_players.setAdapter(localTeamAdapter);

        visitorteam_players = root.findViewById(R.id.visitorteam_players);
        visitorTeamAdapter = new LocalTeamAdapter(getActivity(), vistorteam_players);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        visitorteam_players.setLayoutManager(mLayoutManager1);
        visitorteam_players.setItemAnimator(new DefaultItemAnimator());
        visitorteam_players.setAdapter(visitorTeamAdapter);
        return root;
    }

    public static class LocalTeamAdapter extends RecyclerView.Adapter<LocalTeamAdapter.MyViewHolder> {

        private Context context;
        private ArrayList<ScoreBoard> localteamPlayer_List;

        LocalTeamAdapter(Context applicationContext ,ArrayList<ScoreBoard> localteamPlayer_List) {

            this.context = applicationContext;
            this.localteamPlayer_List = localteamPlayer_List;
        }
        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView playername;

            MyViewHolder(View view) {
                super(view);
                playername = (TextView) view.findViewById(R.id.playername);
            }
        }
        @NonNull
        @Override
        public LocalTeamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_playerslineup, parent, false);

            return new LocalTeamAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LocalTeamAdapter.MyViewHolder holder, int position) {
            final ScoreBoard scoreBoard = localteamPlayer_List.get(position);
            if(scoreBoard.captian.equals("true"))
                holder.playername.setText(scoreBoard.playername + "(c)");
            else if(scoreBoard.wk.equals("true"))
                holder.playername.setText(scoreBoard.playername + "(wk)");
            else if(scoreBoard.wk.equals("true") && scoreBoard.captian.equals("true"))
                holder.playername.setText(scoreBoard.playername + "(c)(wk)");
            else
                holder.playername.setText(scoreBoard.playername);
        }

        @Override
        public int getItemCount() {
            return localteamPlayer_List.size();
        }
    }

    public static class VistorTeamAdapter extends RecyclerView.Adapter<VistorTeamAdapter.MyViewHolder> {

        private Context context;
        private ArrayList<ScoreBoard> visitorteamPlayer_List;

        VistorTeamAdapter(Context applicationContext ,ArrayList<ScoreBoard> visitorteamPlayer_List) {

            this.context = applicationContext;
            this.visitorteamPlayer_List = visitorteamPlayer_List;
        }
        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView playername;

            MyViewHolder(View view) {
                super(view);
                playername = (TextView) view.findViewById(R.id.playername);
            }
        }
        @NonNull
        @Override
        public VistorTeamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_playerslineup, parent, false);

            return new VistorTeamAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull VistorTeamAdapter.MyViewHolder holder, int position) {
            final ScoreBoard scoreBoard = visitorteamPlayer_List.get(position);
            holder.playername.setText(scoreBoard.playername);
        }

        @Override
        public int getItemCount() {
            return visitorteamPlayer_List.size();
        }
    }
}
