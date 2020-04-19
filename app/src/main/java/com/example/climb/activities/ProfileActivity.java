package com.example.climb.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.climb.R;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import static com.parse.Parse.getApplicationContext;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImg;
    private TextView displayName;
    private TextView userBio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null)
        {
            ParseUser currentUser = ParseUser.getCurrentUser();

            profileImg = findViewById(R.id.profileImg);
            ParseFile img = currentUser.getParseFile("profilePhoto");
            if (img != null)
            {
                String imageUrl = img.getUrl();
                Glide.with(getApplicationContext()).load(imageUrl).into(profileImg);

            }

            displayName = findViewById(R.id.displayName);
            String display_name = currentUser.getString("displayName");
            displayName.setText(display_name);

            userBio = findViewById(R.id.userBio);
            String bio = currentUser.getString("bio");
            userBio.setText(bio);
        }
    }

}
