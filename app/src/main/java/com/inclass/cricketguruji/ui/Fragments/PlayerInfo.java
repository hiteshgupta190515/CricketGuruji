package com.inclass.cricketguruji.ui.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.PlayerConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerInfo extends Fragment {

    View root;
    Activity activity;
    CircleImageView playerImage;
    TextView playerName,player_dob,player_gender,player_batstyle,player_bowlstyle,player_position;
    ArrayList<PlayerConstants> playerDetails = new ArrayList<PlayerConstants>();

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

    public static PlayerInfo newInstance( ArrayList<PlayerConstants> playerDetails) {

        PlayerInfo playerInfo = new PlayerInfo();
        Bundle args = new Bundle();
        args.putSerializable("values", playerDetails);
        playerInfo.setArguments(args);

        return playerInfo;
    }

    public PlayerInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         playerDetails = (ArrayList<PlayerConstants>)getArguments().getSerializable("values");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_player_info, container, false);
        playerName = root.findViewById(R.id.player_name);
        playerImage = root.findViewById(R.id.player_image);

        player_dob = root.findViewById(R.id.player_dob);
        player_gender = root.findViewById(R.id.player_gender);
        player_batstyle = root.findViewById(R.id.player_batstyle);
        player_bowlstyle = root.findViewById(R.id.player_bowlstyle);
        player_position = root.findViewById(R.id.player_position);

        playerName.setText(playerDetails.get(0).fullname);
        Picasso.with(getActivity()).load(playerDetails.get(0).image_path).into(playerImage);
        if(playerDetails.get(0).dateofbirth.equals("null"))
            player_dob.setText("N/A");
        else
            player_dob.setText(playerDetails.get(0).dateofbirth);
        if(playerDetails.get(0).gender.equals("null"))
            player_gender.setText("N/A");
        else
            if(playerDetails.get(0).gender.equals("m"))
                player_gender.setText("Male");
            else
                player_gender.setText("Female");
        if(playerDetails.get(0).battingstyle.equals("null"))
            player_batstyle.setText("N/A");
        else
            if(playerDetails.get(0).battingstyle.equals("right-hand-bat"))
                player_batstyle.setText("Right Handed Batsmen");
            else if(playerDetails.get(0).battingstyle.equals("left-hand-bat"))
                player_batstyle.setText("Left Handed Batsmen");
        if(playerDetails.get(0).bowlingstyle.equals("null"))
            player_bowlstyle.setText("N/A");
        else
            player_bowlstyle.setText(capitalizeFirstLetter(playerDetails.get(0).bowlingstyle));
        if(playerDetails.get(0).positionname.equals("null"))
            player_position.setText("N/A");
        else
            player_position.setText(playerDetails.get(0).positionname);

        return root;
    }


    public static String capitalizeFirstLetter(@NonNull String customText){
        int count = customText.length();
        if (count == 0) {
            return customText;
        }
        if (count == 1) {
            return customText.toUpperCase();
        }
        return customText.substring(0, 1).toUpperCase() + customText.substring(1).toLowerCase();
    }
}
