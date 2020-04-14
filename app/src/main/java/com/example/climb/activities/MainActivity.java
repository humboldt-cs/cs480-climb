package com.example.climb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.climb.R;
import com.example.climb.fragments.MapsFragment;
import com.example.climb.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity
{
    public final String TAG = getClass().getSimpleName();
    public static final int LOGIN_REQUEST_CODE = 42;

    NavigationView nvDrawer;
    DrawerLayout drawerLayout;
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nvDrawer = findViewById(R.id.nvDrawer);
        drawerLayout = findViewById(R.id.drawerLayout);
        flContent = findViewById(R.id.flContent);

        FragmentManager fragmentManager = getSupportFragmentManager();

        try
        {
            fragmentManager.beginTransaction().replace(R.id.flContent, ((Fragment) (MapsFragment.class.newInstance()))).commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Unable to load map fragment.", Toast.LENGTH_SHORT).show();
        }

        if (ParseUser.getCurrentUser() != null)
        {
            nvDrawer.getMenu().clear();
            nvDrawer.inflateMenu(R.menu.menu_drawer);
        }
        else
        {
            nvDrawer.getMenu().clear();
            nvDrawer.inflateMenu(R.menu.login_menu_drawer);
        }

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if (item.getItemId() == R.id.menu_logout)
                {
                    ParseUser.logOut();
                    nvDrawer.getMenu().clear();
                    nvDrawer.inflateMenu(R.menu.login_menu_drawer);
                }
                else if (item.getItemId() == R.id.menu_profile)
                {
                    Fragment pFragment = null;
                    try {
                        pFragment = ProfileFragment.class.newInstance();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Unable to load profile.", Toast.LENGTH_SHORT).show();
                    }
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent, pFragment).commit();
                }
                else if (item.getItemId() == R.id.menu_login)
                {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(i, LOGIN_REQUEST_CODE);
                }
                else if (item.getItemId() == R.id.menu_signup)
                {
                    Intent i = new Intent(MainActivity.this, SignupActivity.class);
                    startActivityForResult(i, LOGIN_REQUEST_CODE);
                }


                drawerLayout.closeDrawers();

                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK)
        {
            if (ParseUser.getCurrentUser() != null)
            {
                nvDrawer.getMenu().clear();
                nvDrawer.inflateMenu(R.menu.menu_drawer);
            }
        }
    }
}
