package com.yekuwilfred.checkam.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yekuwilfred.checkam.R;
import com.yekuwilfred.checkam.login.LoginActivity;
import com.yekuwilfred.checkam.main.adapters.PlacesRvAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Place_list_activity extends AppCompatActivity implements PlacesRvAdapter.ItemClickListener {
    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    private PlacesRvAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private static final int PERMISSION_REQUEST_CODE = 100;

    private List<Place> places = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        // addPlaces();
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        if (!isLoggedIn(user)) {
            navigateToLogin();
        }

        RecyclerView placesRecyclerView = findViewById(R.id.place_recyclerview);

        placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlacesRvAdapter(this, this);
        placesRecyclerView.setAdapter(adapter);

        retrievePlaces();

    }

    // Handle the result from requested permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i("Permision", "permission granted");
                } else {
                    Log.i("Permision", "permission NOT granted");
                }
                break;
        }
    }
    //Handle onConnectionFailed


    private void retrievePlaces() throws SecurityException {
        int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            Log.i("Client Connection", "Connected to GoogleClient");
            Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
            placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        places.add(placeLikelihood.getPlace().freeze());
                        Log.i("Place = ", "%d" + placeLikelihood.getPlace());
                        Log.i("Place_list_activity", String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));
                    }
                    adapter.setPlaces(places);
                    likelyPlaces.release();
                }
            });
        }
    }

    public boolean isLoggedIn(FirebaseUser user) {
        boolean logged = false;
        if (user != null) {
            logged = true;
        }
        return logged;
    }

    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @Override
    public void onItemClickListener(Place place) {
        Intent intent = new Intent(this, Place_Detail.class);
        intent.putExtra("place", (Parcelable) place);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
