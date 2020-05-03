package com.example.climb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.climb.R;
import com.example.climb.models.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    // Public member variables
    public final String TAG = getClass().getSimpleName();

    // Private member variables
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button btnProfile;
    FloatingActionButton fabAddLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Find views
        btnProfile = findViewById(R.id.btnProfile);
        fabAddLocation = findViewById(R.id.fabAddLocation);

        // Event listeners
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ParseUser.getCurrentUser() != null)
                {
                    // Launch profile activity for the current user
                    Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    // Send user to login
                    Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MapsActivity.this, .class);
                //startActivity(intent);
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;

        // Query for all locations in the database
        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.include(Location.KEY_NAME);
        query.findInBackground(new FindCallback<Location>()
        {
            @Override
            public void done(List<Location> locations, ParseException e)
            {
                // Iterate through all locations and place markers on the map
                if (e == null)
                {
                    for (Location loc : locations)
                    {
                        if (loc.getLatLong() != null)
                        {
                            LatLng latLng = new LatLng(
                                    loc.getLatLong().getLatitude(),
                                    loc.getLatLong().getLongitude());

                            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(
                                    loc.getName()
                            ));
                            marker.setTag(loc.getObjectId());
                            Log.i(TAG, "Location " + loc.getObjectId() + " added.");
                        }
                    }
                }
                else
                {
                    Log.e("MapsFragment", "Unable to load locations.");
                    e.printStackTrace();
                }
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                final String locationId = marker.getTag().toString();

                Intent intent = new Intent(MapsActivity.this, LocationActivity.class);
                intent.putExtra("locationId", locationId);
                startActivity(intent);
            }
        });
    }
}
