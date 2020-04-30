package com.example.climb.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.climb.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImg;
    private TextView displayName;
    private TextView userBio;
    private Button btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (ParseUser.getCurrentUser() != null)
        {
            ParseUser currentUser = ParseUser.getCurrentUser();

            profileImg = findViewById(R.id.ivProfileImg);
            ParseFile img = currentUser.getParseFile("profilePhoto");
            if (img != null)
            {
                String imageUrl = img.getUrl();
                Glide.with(getApplicationContext()).load(imageUrl).into(profileImg);
            }

            displayName = findViewById(R.id.tvDisplayName);
            String display_name = currentUser.getString("displayName");
            displayName.setText(display_name);

            userBio = findViewById(R.id.tvBio);
            String bio = currentUser.getString("bio");
            userBio.setText(bio);

            btnLogout = findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ParseUser.logOut();
                    finish();
                }
            });
        }
        else
        {
            finish();
        }
    }
}
