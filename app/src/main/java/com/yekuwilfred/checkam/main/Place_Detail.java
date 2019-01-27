package com.yekuwilfred.checkam.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.yekuwilfred.checkam.R;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Place_Detail extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;


    int hasPermission = 0;
    private static final String TAG = Place_Detail.class.getSimpleName();

    LatLng placeLatLng = null;

    String placeName = null;
    String placeAdress = null;
    String placeNumber = null;
    String placeUri = null;
    String placeId = "";
    private TextView web;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place__detail);

      /*  final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
*/

        Intent intent = getIntent();
        Place place = Objects.requireNonNull(intent.getExtras()).getParcelable("place");

       /* mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    Place myPlace = places.get(0);
                    placeName = myPlace.getName().toString();
                    places.release();
                    Log.i(TAG, "onComplete: " + placeName);
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });*/
        assert place != null;
        placeName = place.getName().toString();
        Log.i(TAG, "onComplete: " + placeName);
        TextView pName = findViewById(R.id.place_name);
        pName.setText(placeName);

    }

}
