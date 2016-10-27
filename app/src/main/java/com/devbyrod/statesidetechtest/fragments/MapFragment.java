package com.devbyrod.statesidetechtest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbyrod.statesidetechtest.R;
import com.devbyrod.statesidetechtest.models.Earthquake;
import com.devbyrod.statesidetechtest.models.EarthquakeData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EarthquakeData mEarthquakeData;

    private MapFragmentListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMinZoomPreference(0);
        setEarthquakeData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MapFragmentListener) {
            mListener = (MapFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MapFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onItemClicked(Earthquake earthquake) {

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(earthquake.geometry.coordinates[1], earthquake.geometry.coordinates[0]), 5.0f);
        mMap.animateCamera(update);
    }

    public void setEarthquakesData(EarthquakeData data) {

        mEarthquakeData = data;
        setEarthquakeData();
    }

    private void setEarthquakeData() {

        if(mMap == null || mEarthquakeData == null)
            return;

        for(Earthquake earthquake: mEarthquakeData.earthquakes) {

            LatLng coord = new LatLng(earthquake.geometry.coordinates[1], earthquake.geometry.coordinates[0]);
            mMap.addMarker(new MarkerOptions().position(coord));
        }
    }

    public interface MapFragmentListener {
    }
}
