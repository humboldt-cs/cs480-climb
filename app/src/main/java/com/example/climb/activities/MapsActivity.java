package com.example.climb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.climb.R;
import com.example.climb.models.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    // Public member variables
    public final String TAG = getClass().getSimpleName();

    final int PERMISSION_REQUEST_CODE = 8;
    final String[] REQUIRED_PERMISSIONS =
            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION};
    boolean locationPermissionsGranted = false;

    // Private member variables
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button btnRefresh;
    android.location.Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure all permissions are granted
        if (!allPermissionsGranted())
        {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, PERMISSION_REQUEST_CODE
            );
        }
        else
        {
            locationPermissionsGranted = true;
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = new FusedLocationProviderClient(this);

        // Find views
        btnRefresh = findViewById(R.id.btnRefresh);

        // Event listeners
        btnRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadLocations();
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;

        Task loc = fusedLocationProviderClient.getLastLocation();
        loc.addOnCompleteListener(this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    // Set the map's camera position to the current location of the device.
                    lastKnownLocation = (android.location.Location)task.getResult();

                        LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f));
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.");
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(37.8651, -119.5383), 5.0f));
                }
            }
        });

        loadLocations();

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

    // Process result from permission request dialog box
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            if (checkGrantResults(grantResults))
            {
                locationPermissionsGranted = true;
            }
        }
    }

    // Check if all permissions have been granted
    boolean allPermissionsGranted()
    {
        boolean granted = true;

        for (String p : REQUIRED_PERMISSIONS)
        {
            if (ContextCompat.checkSelfPermission(getBaseContext(), p)
                    != PackageManager.PERMISSION_GRANTED)
            {
                granted = false;
            }
        }

        return granted;
    }

    boolean checkGrantResults(int[] grantResults)
    {
        for (int i = 0; i < grantResults.length; i++)
        {
            if (PackageManager.PERMISSION_GRANTED != grantResults[i])
            {
                return false;
            }
        }

        return true;
    }

    void loadLocations()
    {
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
    }
}
