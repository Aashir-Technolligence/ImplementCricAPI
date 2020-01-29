package com.example.implementcricapi;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    ArrayList<RankingAttributes> addServiceAttrs;
    Activity context;

    public RankingAdapter(ArrayList<RankingAttributes> addServiceAttrs, Activity context) {
        this.context = context;
        this.addServiceAttrs = addServiceAttrs;
    }

    @NonNull
    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking, parent, false);
        return new RankingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.MyViewHolder holder, final int position) {
        holder.rank.setText(addServiceAttrs.get(position).getRank());
        holder.team.setText(addServiceAttrs.get(position).getTeam());
        holder.player.setText(addServiceAttrs.get(position).getPlayer());
        holder.rating.setText(addServiceAttrs.get(position).getRating());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String latitude = addServiceAttrs.get(position).getLat();
//                final String longitude = addServiceAttrs.get(position).getLon();
//
//                Intent intent = new Intent( context, DirectionOnMap.class );
//                intent.putExtra("Latitude", latitude);
//                intent.putExtra("Longitude", longitude);
//                context.startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return addServiceAttrs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rank,player,team , rating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = (TextView) itemView.findViewById(R.id.rank);
            player = (TextView) itemView.findViewById(R.id.player);
            team=(TextView) itemView.findViewById( R.id.team );
            rating=(TextView) itemView.findViewById( R.id.rating );
        }
    }

}
