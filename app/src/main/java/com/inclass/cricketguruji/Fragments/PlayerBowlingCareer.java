package com.inclass.cricketguruji.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inclass.cricketguruji.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerBowlingCareer extends Fragment {
    View root;
    Activity activity;
    RecyclerView bowling_list;
    String[] bowlingHeaderArray;
    public ArrayList<String> playerTestBowlArrayList = new ArrayList<String>();
    public ArrayList<String> playerOdiBowlArrayList = new ArrayList<String>();
    public ArrayList<String> playerT20BowlArrayList = new ArrayList<String>();
    public ArrayList<String> playerIplBowlArrayList = new ArrayList<String>();
    BowlAdapters bowlingAdapters;
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

    public static PlayerBowlingCareer newInstance(String[] bowlingHeaderArray, ArrayList<String> playerTestBowlArrayList,
                                                  ArrayList<String> playerOdiBowlArrayList, ArrayList<String> playerT20BowlArrayList,
                                                  ArrayList<String> playerIplBowlArrayList) {

        PlayerBowlingCareer playerInfo = new PlayerBowlingCareer();
        Bundle args = new Bundle();
        args.putStringArray("bowlingheader", bowlingHeaderArray);
        args.putStringArrayList("test", playerTestBowlArrayList);
        args.putStringArrayList("odi", playerOdiBowlArrayList);
        args.putStringArrayList("t20", playerT20BowlArrayList);
        args.putStringArrayList("ipl", playerIplBowlArrayList);
        playerInfo.setArguments(args);

        return playerInfo;
    }

    public PlayerBowlingCareer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bowlingHeaderArray = getArguments().getStringArray("bowlingheader");
        playerTestBowlArrayList = getArguments().getStringArrayList("test");
        playerOdiBowlArrayList = getArguments().getStringArrayList("odi");
        playerT20BowlArrayList = getArguments().getStringArrayList("t20");
        playerIplBowlArrayList = getArguments().getStringArrayList("ipl");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_bowling_career, container, false);
        bowling_list = (RecyclerView) root.findViewById(R.id.bowling_list);
        bowlingAdapters = new BowlAdapters(getActivity(), bowlingHeaderArray, playerTestBowlArrayList,
                playerOdiBowlArrayList,playerT20BowlArrayList,playerIplBowlArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        bowling_list.setLayoutManager(mLayoutManager);
        bowling_list.setItemAnimator(new DefaultItemAnimator());
        bowling_list.setAdapter(bowlingAdapters);
        return root;
    }

    public static class BowlAdapters extends RecyclerView.Adapter<BowlAdapters.MyViewHolder> {

        private Context context;
        private String[] othersHeaderArray;
        private ArrayList<String> playerTestBowlArrayList;
        private ArrayList<String> playerOdiBowlArrayList;
        private ArrayList<String> playerT20BowlArrayList;
        private ArrayList<String> playerIplBowlArrayList;

        BowlAdapters(Context applicationContext, String[] parentHeaderArray, ArrayList<String> playerTestBowlArrayList,
                        ArrayList<String> playerOdiBowlArrayList,ArrayList<String> playerT20BowlArrayList,
                        ArrayList<String> playerIplBowlArrayList) {

            this.context = applicationContext;
            this.othersHeaderArray = parentHeaderArray;
            this.playerTestBowlArrayList = playerTestBowlArrayList;
            this.playerOdiBowlArrayList = playerOdiBowlArrayList;
            this.playerT20BowlArrayList = playerT20BowlArrayList;
            this.playerIplBowlArrayList = playerIplBowlArrayList;
        }
        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView header,test_ls,odi_ls,t20_ls,ipl_ls;

            MyViewHolder(View view) {
                super(view);
                header = (TextView) view.findViewById(R.id.header);
                test_ls = (TextView) view.findViewById(R.id.test_ls);
                odi_ls = (TextView) view.findViewById(R.id.odi_ls);
                t20_ls = (TextView) view.findViewById(R.id.t20_ls);
                ipl_ls = (TextView) view.findViewById(R.id.ipl_ls);
            }
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_player_batting, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.header.setText(othersHeaderArray[position]);
            holder.test_ls.setText(playerTestBowlArrayList.get(position));
            holder.odi_ls.setText(playerOdiBowlArrayList.get(position));
            holder.t20_ls.setText(playerT20BowlArrayList.get(position));
            holder.ipl_ls.setText(playerIplBowlArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return playerTestBowlArrayList.size();
        }
    }

}
