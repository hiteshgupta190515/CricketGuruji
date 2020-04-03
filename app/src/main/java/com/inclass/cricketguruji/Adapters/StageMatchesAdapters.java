package com.inclass.cricketguruji.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StageMatchesAdapters extends RecyclerView.Adapter<StageMatchesAdapters.MyViewHolder> {

    ArrayList<Constants> itemsList;
    LayoutInflater infalter;
    Context ctx;
    String stageid;

    public StageMatchesAdapters(Context context, ArrayList<Constants> itemsList) {
        this.ctx = context;
        this.infalter = LayoutInflater.from(ctx);
        this.itemsList = itemsList;
        this.stageid = stageid;
    }


    @NonNull
    @Override
    public StageMatchesAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = infalter.inflate(R.layout.item_match_layout, parent, false);

        return new StageMatchesAdapters.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull StageMatchesAdapters.MyViewHolder holder, int position) {
        Constants singleItem = itemsList.get(position);
        holder.round.setText(singleItem.round);
        holder.localteam.setText(singleItem.localteam);
        holder.visitorteam.setText(singleItem.visitorteam);
        holder.datetime.setText("Match starts on " + singleItem.startingdate + " at " + singleItem.startingtime);
        Picasso.with(ctx).load(singleItem.localimage).into(holder.localteam_image);
        Picasso.with(ctx).load(singleItem.visitorimage).into(holder.visitorteam_image);
    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView round, localteam, visitorteam, datetime;
        ImageView localteam_image, visitorteam_image;

        MyViewHolder(View itemView) {
            super(itemView);
            round = itemView.findViewById(R.id.round);
            localteam = itemView.findViewById(R.id.localteam);
            visitorteam = itemView.findViewById(R.id.visitorteam);
            datetime = itemView.findViewById(R.id.datetime);
            localteam_image = itemView.findViewById(R.id.localteam_image);
            visitorteam_image = itemView.findViewById(R.id.visitorteam_image);
        }
    }

}
