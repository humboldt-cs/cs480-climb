package com.example.climb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.climb.R;
import com.example.climb.models.Photo;
import com.example.climb.models.Route;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ClimbActivity extends AppCompatActivity
{
    Route route;

    TextView tvRouteName;
    TextView tvRouteDifficulty;
    TextView tvRouteDescription;

    ImageView ivRouteThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb);

        route = getIntent().getParcelableExtra("route");

        tvRouteName = findViewById(R.id.tvRouteName);
        tvRouteDifficulty = findViewById(R.id.tvRouteDifficulty);
        tvRouteDescription = findViewById(R.id.tvRouteDescription);
        ivRouteThumbnail = findViewById(R.id.ivRouteThumbnail);

        tvRouteName.setText(route.getName());
        tvRouteDifficulty.setText(route.getDifficulty());
        tvRouteDescription.setText(route.getDescription());

        if (route.getThumbnail() != null)
        {
            Glide.with(getApplicationContext())
                    .load(route.getThumbnail().getUrl())
                    .into(ivRouteThumbnail);
        }

        ParseObject dummyRoute = ParseObject.createWithoutData("Route", route.getObjectId());
        ParseQuery query = ParseQuery.getQuery(Photo.class);
        query.whereEqualTo("route", route);
        query.findInBackground(new FindCallback<Photo>()
        {
            @Override
            public void done(List<Photo> routes, ParseException e)
            {

            }
        });
    }
}
