package com.yekuwilfred.checkam.tabsFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.yekuwilfred.checkam.R;
import com.yekuwilfred.checkam.adapters.PlacesAdapter;

import java.util.ArrayList;

/**
 * Created by YEKUWILFRED on 4/13/2017.
 */

public class ListPlacesTypeFragment extends Fragment implements AdapterView.OnItemClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String LOG_TAG = "ListFragment";
    private static final int PERMISSION_REQUEST_CODE = 100;
    protected GoogleApiClient mGoogleApiClient;
    PlacesAdapter adapter = null;
    int hasPermission = 0;
    private ArrayList<Place> placeATM = new ArrayList<>();
    private ArrayList<Place> placeAco = new ArrayList<>();
    private ArrayList<Place> placeBakery = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), this)
                .build();
        ListView listView = getActivity().findViewById(R.id.list);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.types_of_places, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        retrievePlacesType();

    }
    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onResume(){
        super.onResume();
        mGoogleApiClient.connect();
    }
    @Override
    public void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                placeATM.get(position);
            case 1:
                placeAco.get(position);
            case 2:
                placeBakery.get(position);
        }

        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_places_fragment, container, false);
    }

    private void retrievePlacesType() throws SecurityException {
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    for (int i = 0; i < placeLikelihood.getPlace().getPlaceTypes().size(); i++) {
                        if (placeLikelihood.getPlace().getPlaceTypes().get(i) == Place.TYPE_ATM) {
                            placeATM.add(placeLikelihood.getPlace().freeze());
                            Log.i("Place = ", "%d" + placeLikelihood.getPlace());
                        } else if (placeLikelihood.getPlace().getPlaceTypes().get(i) == Place.TYPE_ACCOUNTING) {
                            placeAco.add(placeLikelihood.getPlace().freeze());
                        } else if (placeLikelihood.getPlace().getPlaceTypes().get(i) == Place.TYPE_BAKERY) {
                            placeBakery.add(placeLikelihood.getPlace().freeze());
                        }
                    }
                }
                likelyPlaces.release();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: " + connectionResult.getErrorCode());
        Toast.makeText(getActivity(), "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }
}