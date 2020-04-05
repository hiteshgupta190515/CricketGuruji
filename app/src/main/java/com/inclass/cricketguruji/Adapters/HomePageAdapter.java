package com.inclass.cricketguruji.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.LiveMatch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomePageAdapter extends PagerAdapter {

    private ArrayList<LiveMatch> liveMatches;
    Context context;
    LayoutInflater layoutInflater;

    public HomePageAdapter(ArrayList<LiveMatch> liveMatches, Context context) {
        this.liveMatches = liveMatches;
        this.context = context;
    }

    @Override
    public int getCount() {
        return liveMatches.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view  = layoutInflater.inflate(R.layout.item_home_livematch,container,false);

        ImageView team1_img,team2_img;
        TextView team1_name,team2_name,season_name;

        team1_img = view.findViewById(R.id.team1_img);
        team2_img = view.findViewById(R.id.team2_img);
        team1_name = view.findViewById(R.id.team1_name);
        team2_name = view.findViewById(R.id.team2_name);
        season_name = view.findViewById(R.id.season_name);
        final LiveMatch p = liveMatches.get(position);
        team1_name.setText(p.localteam);
        Picasso.with(context).load(p.localteam_image).placeholder(R.drawable.flag_avatar).into(team1_img);
        team2_name.setText(p.visitorteam);
        Picasso.with(context).load(p.visitorTeam_image).placeholder(R.drawable.flag_avatar).into(team2_img);
        season_name.setText(p.season_name);

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
