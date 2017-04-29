package com.yekuwilfred.checkam.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.yekuwilfred.checkam.R;

import java.util.ArrayList;

/**
 * Created by YEKUWILFRED on 4/15/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter <PlacesAdapter.PlaceViewHolder>{

    ArrayList<Place> places = new ArrayList<>();

    public PlacesAdapter(ArrayList<Place> places){
        this.places = places;
    }


    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        holder.placeName.setText(places.get(position).getName().toString());
        holder.telephone.setText(places.get(position).getPhoneNumber().toString());
        holder.adress.setText(places.get(position).getAddress().toString());
        holder.location.setText(places.get(position).getLocale().getDisplayName());
        //holder.placeName.setText( places.get(position).getTelephone());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return places.size();// places.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder{
        TextView placeName;
        TextView telephone;
        TextView adress;
        TextView location;
        TextView website;
        CardView cardView;
        

        public PlaceViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            placeName = (TextView)itemView.findViewById(R.id.place_name);
            telephone = (TextView)itemView.findViewById(R.id.telephone);
            adress = (TextView)itemView.findViewById(R.id.adress);
            location = (TextView)itemView.findViewById(R.id.location);
            website = (TextView)itemView.findViewById(R.id.website);
        }
    }
}
