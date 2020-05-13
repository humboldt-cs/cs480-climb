package com.example.climb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.climb.LocationAdapter;
import com.example.climb.R;
import com.example.climb.models.Location;
import com.example.climb.models.Route;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity
{
    public static final String TAG = "LocationActivity";

    private TextView tvLocationName;
    private TextView tvLocationDescription;
    private TextView tvLocationLatLong;
    private String locationId;
    private RecyclerView rvRoutes;
    protected LocationAdapter adapter;
    protected List <Route> allRoutes;

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

        rvRoutes = findViewById(R.id.rvRoutes);
        allRoutes = new ArrayList<>();
        adapter = new LocationAdapter(this, allRoutes);
        rvRoutes.setAdapter(adapter);
        rvRoutes.setLayoutManager(new LinearLayoutManager(this));
        queryRoutes();


    }
    protected void queryRoutes() {
        ParseObject dummyLocation = ParseObject.createWithoutData("Location", locationId);
        ParseQuery query2 = ParseQuery.getQuery(Route.class);
        query2.whereEqualTo("location", dummyLocation);
        query2.findInBackground(new FindCallback<Route>()
        {
            @Override
            public void done(List<Route> routes, ParseException e)
            {
                if (e != null)
                {
                    Log.d(TAG, "Error querying parse for routes");
                }
                Log.i(TAG, "Adding routes...");
                for (Route route : routes)
                {
                    Log.i(TAG, route.getName());
                }
                adapter.addAll(routes);
            }
        });
    }


}
