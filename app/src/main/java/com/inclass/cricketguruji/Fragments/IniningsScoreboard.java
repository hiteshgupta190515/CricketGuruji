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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.ScoreBoard;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IniningsScoreboard extends Fragment {

    View root;
    Activity activity;
    public ArrayList<ScoreBoard> batscoreBoardArrayList = new ArrayList<ScoreBoard>();
    public ArrayList<ScoreBoard> bowlscoreBoardArrayList = new ArrayList<ScoreBoard>();
    private RecyclerView battingscoreboards,bowlingscoreboards;
    ScoreBattingAdapters battingAdapters;
    ScoreBowlingAdapters bowlingAdapters;
    TextView batting_team,bowling_team;
    LinearLayout tab1layout,tab2layout;
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

    public static IniningsScoreboard newInstance(ArrayList<ScoreBoard> battingArrayList, ArrayList<ScoreBoard> bowlingArrayList) {
        IniningsScoreboard fragment = new IniningsScoreboard();
        Bundle args = new Bundle();
        args.putSerializable("batting", battingArrayList);
        args.putSerializable("bowling", bowlingArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    public IniningsScoreboard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        batscoreBoardArrayList = (ArrayList<ScoreBoard>)getArguments().getSerializable("batting");
        bowlscoreBoardArrayList = (ArrayList<ScoreBoard>)getArguments().getSerializable("bowling");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.ininings_scoreboard, container, false);
        battingscoreboards = root.findViewById(R.id.battingscoreboards);
        bowlingscoreboards = root.findViewById(R.id.bowlingscoreboards);
        batting_team = root.findViewById(R.id.batting_team);
        bowling_team = root.findViewById(R.id.bowling_team);
        tab1layout = root.findViewById(R.id.tab1layout);
        tab2layout = root.findViewById(R.id.tab2layout);

        tab1layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(battingscoreboards.getVisibility() == View.GONE)
                    battingscoreboards.setVisibility(View.VISIBLE);
                else
                    battingscoreboards.setVisibility(View.GONE);
            }
        });
        tab2layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bowlingscoreboards.getVisibility() == View.GONE)
                    bowlingscoreboards.setVisibility(View.VISIBLE);
                else
                    bowlingscoreboards.setVisibility(View.GONE);
            }
        });

        batting_team.setText(batscoreBoardArrayList.get(0).battingteam + " - Batting");
        bowling_team.setText(bowlscoreBoardArrayList.get(0).bowlingteam + " - Bowling");

        battingAdapters = new ScoreBattingAdapters(getActivity(), batscoreBoardArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        battingscoreboards.setLayoutManager(mLayoutManager);
        battingscoreboards.setItemAnimator(new DefaultItemAnimator());
        battingscoreboards.setAdapter(battingAdapters);

        bowlingAdapters = new ScoreBowlingAdapters(getActivity(), bowlscoreBoardArrayList);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        bowlingscoreboards.setLayoutManager(mLayoutManager1);
        bowlingscoreboards.setItemAnimator(new DefaultItemAnimator());
        bowlingscoreboards.setAdapter(bowlingAdapters);

        return root;
    }

    public static class ScoreBattingAdapters extends RecyclerView.Adapter<ScoreBattingAdapters.MyViewHolder> {

        private Context context;
        private ArrayList<ScoreBoard> battingArrayList;

        ScoreBattingAdapters(Context applicationContext, ArrayList<ScoreBoard> battingArrayList) {

            this.context = applicationContext;
            this.battingArrayList = battingArrayList;
        }
        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView batsmanname,batsmanruns,batsmanballs,batsmanfour,batsmansix,batsmansr,
                    catchout,catchout_player,bowled,bowled_player;

            MyViewHolder(View view) {
                super(view);
                batsmanname = (TextView) view.findViewById(R.id.batsmanname);
                batsmanruns = (TextView) view.findViewById(R.id.batsmanruns);
                batsmanballs = (TextView) view.findViewById(R.id.batsmanballs);
                batsmanfour = (TextView) view.findViewById(R.id.batsmanfour);
                batsmansix = (TextView) view.findViewById(R.id.batsmansix);
                batsmansr = (TextView) view.findViewById(R.id.batsmansr);
                catchout = (TextView) view.findViewById(R.id.catchout);
                catchout_player = (TextView) view.findViewById(R.id.catchout_player);
                bowled = (TextView) view.findViewById(R.id.bowled);
                bowled_player = (TextView) view.findViewById(R.id.bowled_player);
            }
        }
        @NonNull
        @Override
        public ScoreBattingAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_batting_scorecard, parent, false);

            return new ScoreBattingAdapters.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ScoreBattingAdapters.MyViewHolder holder, int position) {
            final ScoreBoard p = battingArrayList.get(position);
            holder.batsmanname.setText(p.batsman);
            holder.batsmanruns.setText(p.runs);
            holder.batsmanballs.setText(p.balls);
            holder.batsmanfour.setText(p.four);
            holder.batsmansix.setText(p.six);
            holder.batsmansr.setText(p.sr);
            if (p.bowlerout!=null && p.catchout!=null){
                holder.catchout_player.setText(p.catchout);
                holder.bowled_player.setText(p.bowlerout);
            } else if (p.bowlerout ==null && p.catchout!=null){
                holder.catchout.setText("out");
                holder.catchout_player.setText(p.catchout);
                holder.bowled_player.setVisibility(View.GONE);
                holder.bowled.setVisibility(View.GONE);
            } else if (p.bowlerout !=null && p.catchout==null) {
                holder.catchout.setText("b");
                holder.catchout_player.setText(p.bowlerout);
                holder.bowled.setVisibility(View.GONE);
                holder.bowled_player.setVisibility(View.GONE);
            } else {
                holder.catchout.setText("Not Out");
                holder.bowled.setVisibility(View.GONE);
                holder.bowled_player.setVisibility(View.GONE);
                holder.catchout_player.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return battingArrayList.size();
        }
    }

    public static class ScoreBowlingAdapters extends RecyclerView.Adapter<ScoreBowlingAdapters.MyViewHolder> {

        private Context context;
        private ArrayList<ScoreBoard> bowlerArrayList;

        ScoreBowlingAdapters(Context applicationContext, ArrayList<ScoreBoard> bowlerArrayList) {

            this.context = applicationContext;
            this.bowlerArrayList = bowlerArrayList;
        }
        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView bowlername,bowlerovers,bowlermad,bowlerruns,bowlerwicket,bowlerwd,
                    bowlernb;

            MyViewHolder(View view) {
                super(view);
                bowlername = (TextView) view.findViewById(R.id.bowlername);
                bowlerovers = (TextView) view.findViewById(R.id.bowlerovers);
                bowlermad = (TextView) view.findViewById(R.id.bowlermad);
                bowlerruns = (TextView) view.findViewById(R.id.bowlerruns);
                bowlerwicket = (TextView) view.findViewById(R.id.bowlerwicket);
                bowlerwd = (TextView) view.findViewById(R.id.bowlerwd);
                bowlernb = (TextView) view.findViewById(R.id.bowlernb);
            }
        }
        @NonNull
        @Override
        public ScoreBowlingAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bowler_scorecard, parent, false);

            return new ScoreBowlingAdapters.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ScoreBowlingAdapters.MyViewHolder holder, int position) {
            final ScoreBoard p = bowlerArrayList.get(position);
            holder.bowlername.setText(p.bowler);
            holder.bowlerovers.setText(p.overs);
            holder.bowlermad.setText(p.madiens);
            holder.bowlerruns.setText(p.bowlerruns);
            holder.bowlerwicket.setText(p.wickets);
            holder.bowlerwd.setText(p.wide);
            holder.bowlernb.setText(p.noballs);
        }

        @Override
        public int getItemCount() {
            return bowlerArrayList.size();
        }
    }
}
