package com.example.climb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.climb.R;
import com.example.climb.fragments.ClimbFragment;
import com.example.climb.fragments.LocationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
// CreateActivity contains two fragments - create route and create climb, with a switch button at
// the top that allows the user to pick which one they want to create

public class CreateActivity extends AppCompatActivity {

    // TODO: replace this with something nicer than a bottom navigation bar to switch between fragments
    public static final String TAG = "CreateActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_climb:
                        fragment = new ClimbFragment();
                        break;
                    case R.id.action_location:
                        fragment = new LocationFragment();
                        break;
                    default:
                        fragment = new ClimbFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_climb);
    }

}
