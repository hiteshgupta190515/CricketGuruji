package com.inclass.cricketguruji.ui.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.PlayerConstants;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerBattingCareer extends Fragment {
    View root;
    Activity activity;
    RecyclerView batting_list;
    String[] battingHeaderArray;
    public ArrayList<String> playerTestBatArrayList = new ArrayList<String>();
    public ArrayList<String> playerOdiBatArrayList = new ArrayList<String>();
    public ArrayList<String> playerT20BatArrayList = new ArrayList<String>();
    public ArrayList<String> playerIplBatArrayList = new ArrayList<String>();
    BattingAdapters battingAdapters;
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

    public static PlayerBattingCareer newInstance( String[] battingHeaderArray, ArrayList<String> playerTestBatArrayList,
                                          ArrayList<String> playerOdiBatArrayList,ArrayList<String> playerT20BatArrayList,
                                          ArrayList<String> playerIplBatArrayList) {

        PlayerBattingCareer playerInfo = new PlayerBattingCareer();
        Bundle args = new Bundle();
        args.putStringArray("battingheader", battingHeaderArray);
        args.putStringArrayList("test", playerTestBatArrayList);
        args.putStringArrayList("odi", playerOdiBatArrayList);
        args.putStringArrayList("t20", playerT20BatArrayList);
        args.putStringArrayList("ipl", playerIplBatArrayList);
        playerInfo.setArguments(args);

        return playerInfo;
    }

    public PlayerBattingCareer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        battingHeaderArray = getArguments().getStringArray("battingheader");
        playerTestBatArrayList = getArguments().getStringArrayList("test");
        playerOdiBatArrayList = getArguments().getStringArrayList("odi");
        playerT20BatArrayList = getArguments().getStringArrayList("t20");
        playerIplBatArrayList = getArguments().getStringArrayList("ipl");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_batting_career, container, false);
        batting_list = (RecyclerView) root.findViewById(R.id.batting_list);
        battingAdapters = new BattingAdapters(getActivity(), battingHeaderArray, playerTestBatArrayList,
                playerOdiBatArrayList,playerT20BatArrayList,playerIplBatArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        batting_list.setLayoutManager(mLayoutManager);
        batting_list.setItemAnimator(new DefaultItemAnimator());
        batting_list.setAdapter(battingAdapters);
        return root;
    }

    public static class BattingAdapters extends RecyclerView.Adapter<BattingAdapters.MyViewHolder> {

        private Context context;
        private String[] othersHeaderArray;
        private ArrayList<String> playerTestBatArrayList;
        private ArrayList<String> playerOdiBatArrayList;
        private ArrayList<String> playerT20BatArrayList;
        private ArrayList<String> playerIplBatArrayList;

        BattingAdapters(Context applicationContext, String[] parentHeaderArray, ArrayList<String> playerTestBatArrayList,
                        ArrayList<String> playerOdiBatArrayList,ArrayList<String> playerT20BatArrayList,
                        ArrayList<String> playerIplBatArrayList) {

            this.context = applicationContext;
            this.othersHeaderArray = parentHeaderArray;
            this.playerTestBatArrayList = playerTestBatArrayList;
            this.playerOdiBatArrayList = playerOdiBatArrayList;
            this.playerT20BatArrayList = playerT20BatArrayList;
            this.playerIplBatArrayList = playerIplBatArrayList;
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
            holder.test_ls.setText(playerTestBatArrayList.get(position));
            holder.odi_ls.setText(playerOdiBatArrayList.get(position));
            holder.t20_ls.setText(playerT20BatArrayList.get(position));
            holder.ipl_ls.setText(playerIplBatArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return playerTestBatArrayList.size();
        }
    }

}
