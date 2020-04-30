package com.example.climb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.climb.R;
import com.example.climb.models.Location;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class LocationActivity extends AppCompatActivity
{
    public static final String TAG = "LocationActivity";

    TextView tvLocationName;
    TextView tvLocationDescription;
    TextView tvLocationLatLong;
    String locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationId = getIntent().getStringExtra("locationId");

        if (locationId == null)
        {
            finish();
        }

        tvLocationName = findViewById(R.id.tvLocationName);
        tvLocationDescription = findViewById(R.id.tvLocationDescription);
        tvLocationLatLong = findViewById(R.id.tvLocationLatLong);

        ParseQuery query = ParseQuery.getQuery(Location.class);
        query.getInBackground(locationId, new GetCallback<Location>()
        {
            @Override
            public void done(Location location, ParseException e)
            {
                if (e != null)
                {
                    Log.e(TAG, "Error while querying database for location.");
                    e.printStackTrace();
                    return;
                }

                tvLocationName.setText(location.getName());
                tvLocationDescription.setText(location.getDescription());
                tvLocationLatLong.setText(location.getLatLong().toString());
            }
        });
    }
}
