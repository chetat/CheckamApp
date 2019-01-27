package com.yekuwilfred.checkam.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.yekuwilfred.checkam.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YEKUWILFRED on 4/15/2017.
 */

public class PlacesRvAdapter extends RecyclerView.Adapter<PlacesRvAdapter.PlaceViewHolder> {
    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    private Context mContext;
    private List<Place> mPlaces;

    public PlacesRvAdapter(Context context, ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
       this.mContext = context;
    }


    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlaceViewHolder holder, final int position) {
        final LatLng placelng = mPlaces.get(position).getLatLng();
        final String placeAdress = mPlaces.get(position).getAddress().toString();
        final String placeNumber = mPlaces.get(position).getPhoneNumber().toString();
        final String placeName = mPlaces.get(position).getName().toString();
        holder.placeName.setText(placeName);
    }

    @Override
    public int getItemCount() {
        if (mPlaces == null) {
            return 0;// places.size();
        }
        return mPlaces.size();
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setPlaces(List<Place> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(Place place);
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView placeName;


        PlaceViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            placeName = itemView.findViewById(R.id.place_name);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                Place place = mPlaces.get(position);
                mItemClickListener.onItemClickListener(place);
            }
        }
    }
}