package com.inclass.cricketguruji.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.Constants;
import com.inclass.cricketguruji.Fragments.LiveMatchesFragment;
import com.inclass.cricketguruji.Fragments.RecentMatchesFragment;

import java.util.ArrayList;

public class RecentMatches_StageAdapters extends RecyclerView.Adapter<RecentMatches_StageAdapters.MyViewHolder> {

    RecentMatchesFragment main;
    LayoutInflater infalter;
    Context ctx;
    public RecentStageMatchesAdapters stageMatchesAdapters;

    public RecentMatches_StageAdapters(Context context, RecentMatchesFragment main) {
        this.ctx = context;
        this.infalter = LayoutInflater.from(ctx);
        this.main = main;
    }


    @NonNull
    @Override
    public RecentMatches_StageAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = infalter.inflate(R.layout.item_stage, parent, false);

        return new RecentMatches_StageAdapters.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentMatches_StageAdapters.MyViewHolder holder, int position) {
        final ArrayList<Constants> singleSectionItems = this.main.finalfixtures.get(position).getAllItemsInSection();
        holder.title_section.setText(this.main.noRepeatStage.get(position).stageName);

        stageMatchesAdapters = new RecentStageMatchesAdapters(ctx, singleSectionItems);
        holder.stage_matches_recyclerView.setHasFixedSize(true);
        holder.stage_matches_recyclerView.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, true));
        holder.stage_matches_recyclerView.setAdapter(stageMatchesAdapters);
    }


    @Override
    public int getItemCount() {
        return this.main.finalfixtures.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_section;
        RecyclerView stage_matches_recyclerView;

        MyViewHolder(View itemView) {
            super(itemView);
            title_section = itemView.findViewById(R.id.title_section);
            stage_matches_recyclerView = itemView.findViewById(R.id.stage_matches_recyclerView);
        }
    }

}
