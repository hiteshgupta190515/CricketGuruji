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
import com.inclass.cricketguruji.ui.Fragments.LiveMatchesFragment;

import java.util.ArrayList;

public class LiveMatches_StageAdapters extends RecyclerView.Adapter<LiveMatches_StageAdapters.MyViewHolder> {

    LiveMatchesFragment main;
    LayoutInflater infalter;
    Context ctx;
    public StageMatchesAdapters stageMatchesAdapters;

    public LiveMatches_StageAdapters(Context context, LiveMatchesFragment main) {
        this.ctx = context;
        this.infalter = LayoutInflater.from(ctx);
        this.main = main;
    }


    @NonNull
    @Override
    public LiveMatches_StageAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = infalter.inflate(R.layout.item_stage, parent, false);

        return new LiveMatches_StageAdapters.MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveMatches_StageAdapters.MyViewHolder holder, int position) {
        final ArrayList<Constants> singleSectionItems = this.main.finalfixtures.get(position).getAllItemsInSection();
        holder.title_section.setText(this.main.noRepeatStage.get(position).stageName);

        stageMatchesAdapters = new StageMatchesAdapters(ctx, singleSectionItems);
        holder.stage_matches_recyclerView.setHasFixedSize(true);
        holder.stage_matches_recyclerView.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
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
