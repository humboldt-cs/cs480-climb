package com.example.climb.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.climb.R;
import com.example.climb.models.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback
{
    GoogleMap map;
    MapView mapView;

    public MapsFragment()
    {
        // Required empty public constructor
    }

    public static MapsFragment newInstance(String param1, String param2)
    {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Why does this work with getChildFragmentManager?
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.include(Location.KEY_NAME);
        query.findInBackground(new FindCallback<Location>()
        {
            @Override
            public void done(List<Location> locations, ParseException e)
            {
                if (e == null)
                {
                    for (Location loc : locations)
                    {
                        LatLng latLng = new LatLng(
                                loc.getLatLong().getLatitude(),
                                loc.getLatLong().getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng).title(
                                loc.getName()
                        ));
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
                else
                {
                    Log.e("MapsFragment", "Unable to load locations.");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
    }
}
